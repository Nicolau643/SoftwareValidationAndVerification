package vvs_webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public class TestUtils {
	
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";

	public static String addCustomer(HtmlPage page) throws MalformedURLException, IOException {
		final String vat = "123456789";
		final String designation = "Marta";
		final String phone = "987645264";
		
		HtmlAnchor addCostumerLink = page.getAnchorByHref("addCustomer.html");
		
		HtmlPage nextPage = (HtmlPage) addCostumerLink.openLinkInNewWindow();
		
		assertEquals("not in the correct page","Enter Name", nextPage.getTitleText());
		
		HtmlForm addCostumerForm = nextPage.getForms().get(0);
		
		HtmlInput vatInput = addCostumerForm.getInputByName("vat");
		vatInput.setValueAttribute(vat);
		HtmlInput desInput = addCostumerForm.getInputByName("designation");
		desInput.setValueAttribute(designation);
		HtmlInput phoneInput = addCostumerForm.getInputByName("phone");
		phoneInput.setValueAttribute(phone);
		
		HtmlInput submit = addCostumerForm.getInputByName("submit");
		submit.click();
		return vat;
	}
	
	public static String addSale(HtmlPage page, String vat) throws MalformedURLException, IOException {
		final String id;
		HtmlPage nextPage;
		HtmlAnchor addSaleLink = page.getAnchorByHref("addSale.html");
		nextPage = (HtmlPage) addSaleLink.openLinkInNewWindow();
		assertEquals("not in the correct page","New Sale", nextPage.getTitleText());
	
		HtmlPage reportPage;
        String formData = String.format("customerVat=%s", vat);
                
        WebRequest req =
            new WebRequest(new java.net.URL(APPLICATION_URL+"AddSalePageController"),
                           HttpMethod.POST);
        req.setRequestBody(formData);
        
        WebClient webClient = new WebClient(BrowserVersion.getDefault());
           
        reportPage = (HtmlPage) webClient.getPage(req);
        
		webClient.close();

		Object[] tables = reportPage.getByXPath("//table").toArray();
        
        if (tables.length == 0) {
			fail();
		}
        HtmlTable table = (HtmlTable) tables[0];
        
        id = table.getRows().get(table.getRows().size()-1).getCell(0).asText();
        
        assertEquals("not in the correct page","Sales Info", reportPage.getTitleText());
        System.out.println("saleId: "+id);
		return id;
	}
	
	
	public static void addAdress(final String vat, final String adress1, final String door1, final String zip1,
			final String location1, HtmlAnchor addAdressLink) throws MalformedURLException, IOException {
		
		HtmlPage nextPage = (HtmlPage) addAdressLink.openLinkInNewWindow();
		
		assertEquals("not in the correct page","Enter Address", nextPage.getTitleText());
		
		HtmlForm addAdressForm = nextPage.getForms().get(0);
		
		HtmlInput vatInput = addAdressForm.getInputByName("vat");
		vatInput.setValueAttribute(vat);
		HtmlInput adressInput = addAdressForm.getInputByName("address");
		adressInput.setValueAttribute(adress1);
		HtmlInput doorInput = addAdressForm.getInputByName("door");
		doorInput.setValueAttribute(door1);
		HtmlInput zipInput = addAdressForm.getInputByName("postalCode");
		zipInput.setValueAttribute(zip1);
		HtmlInput localInput = addAdressForm.getInputByName("locality");
		localInput.setValueAttribute(location1);
		
		HtmlInput submit = addAdressForm.getInputByValue("Insert");
		submit.click();
	
	}
	
	public static void remove(HtmlPage page,String vat) throws MalformedURLException, IOException {
		HtmlPage nextPage;
		HtmlInput vatInput;
		HtmlInput submit;
		HtmlAnchor removeCustomerLink = page.getAnchorByHref("RemoveCustomerPageController");
		nextPage = (HtmlPage) removeCustomerLink.openLinkInNewWindow();
		assertTrue(nextPage.asText().contains(vat));
		
		HtmlForm removeCustomerForm = nextPage.getForms().get(0);
		vatInput = removeCustomerForm.getInputByName("vat");
		vatInput.setValueAttribute(vat);
		submit = removeCustomerForm.getInputByName("submit");
		submit.click();
	}
	
}
