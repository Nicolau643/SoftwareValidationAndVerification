package vvs_webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public class Test5 {

	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";
	
	private static HtmlPage page;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
	
			// possible configurations needed to prevent JUnit tests to fail for complex HTML pages
            webClient.setJavaScriptTimeout(15000);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
		    
			page = webClient.getPage(APPLICATION_URL);
			assertEquals(200, page.getWebResponse().getStatusCode()); // OK status
		}
	}
	
	private static String vat;
	
	/*
	 * create a new customer, create a new sale for her, insert a delivery 
	 * for that sale and then show the sale delivery. Check that all intermediate 
	 * pages have the expected information.
	 */
	@Test
	public void test5() throws MalformedURLException, IOException {
		
		final String id_sale;
		
		final String adress1 = "address";
		final String door1 = "3";
		final String zip1 = "3333-333";
		final String location1 = "location";
		
		//add client
		HtmlInput submit;
		vat = TestUtils.addCustomer(page);
		
		//add adress
		TestUtils.addAdress(vat, adress1, door1, zip1, location1, page.getAnchorByHref("addAddressToCustomer.html"));
		
		// add sale
		HtmlPage reportPage;
		Object[] tables;
		id_sale = TestUtils.addSale(page,vat);
		
		HtmlAnchor addCostumerLink = page.getAnchorByHref("saleDeliveryVat.html");
		
		HtmlPage nextPage = (HtmlPage) addCostumerLink.openLinkInNewWindow();
		
		assertEquals("not in the correct page","Enter Name", nextPage.getTitleText());
		
		reportPage = getPage(vat,"AddSaleDeliveryPageController");

		assertEquals("not in the correct page","Enter Name", reportPage.getTitleText());
		
		//check the address table
		tables = reportPage.getByXPath("//table").toArray();
        
        if (tables.length == 0 || tables.length == 1) {
			fail();
		}
        
        HtmlTable addressTable = (HtmlTable) tables[0];
        String addressRowText = addressTable.getRow(addressTable.getRowCount()-1).asText();
        
        assertEquals(true,addressRowText.contains("address") &&
        		addressRowText.contains("3") &&
        		addressRowText.contains("3333-333")&&
        		addressRowText.contains("location"));
        
        final String id_adress = addressTable.getRow(addressTable.getRowCount()-1).getCell(0).asText();
        
        //check the sales table
        HtmlTable salesTable = (HtmlTable) tables[1];
        String idSaleText = salesTable.getRow(salesTable.getRowCount()-1).getCell(0).asText();
        String vatSaleText = salesTable.getRow(salesTable.getRowCount()-1).getCell(4).asText();
        
        assertEquals(true,idSaleText.equals(id_sale) && vatSaleText.equals(vat));
        
        //add info to the form        
		HtmlForm adressSaleForm = reportPage.getForms().get(0);
		
		HtmlInput addressInput = adressSaleForm.getInputByName("addr_id");
		addressInput.setValueAttribute(id_adress);
		
		HtmlInput saleInput = adressSaleForm.getInputByName("sale_id");
		saleInput.setValueAttribute(id_sale);
		
		submit = adressSaleForm.getInputByValue("Insert");
		submit.click();
		
		HtmlAnchor showDeliveryLink = page.getAnchorByHref("showDelivery.html");
		
		nextPage = (HtmlPage) showDeliveryLink.openLinkInNewWindow();
		
		assertEquals("not in the correct page","Enter Name", nextPage.getTitleText());
		
		reportPage = getPage(vat,"GetSaleDeliveryPageController");
		
		assertEquals("not in the correct page","Sales Info", reportPage.getTitleText());
		
		tables = reportPage.getByXPath("//table").toArray();
        
        assertEquals(1,tables.length);
        
        HtmlTable salesDelivTable = (HtmlTable) tables[0];
        String saleIdSaleDelivText = salesDelivTable.getRow(salesDelivTable.getRowCount()-1).getCell(1).asText();
        String addressidSaleDelivText = salesDelivTable.getRow(salesDelivTable.getRowCount()-1).getCell(2).asText();

        assertEquals(true,saleIdSaleDelivText.equals(id_sale) && addressidSaleDelivText.equals(id_adress));
        
	}
	
	@After
	public void teardown() throws MalformedURLException, IOException {
		 TestUtils.remove(page,vat);
	}


	private HtmlPage getPage(final String vat, String controller) throws MalformedURLException, IOException {
		HtmlPage reportPage;
		WebRequest req;
		WebClient webClient;
		String formData = String.format("vat=%s", vat);
                
        req = new WebRequest(new java.net.URL(APPLICATION_URL+controller),
                           HttpMethod.POST);
        req.setRequestBody(formData);
        
        webClient = new WebClient(BrowserVersion.getDefault());
           
        reportPage = (HtmlPage) webClient.getPage(req);
        
		webClient.close();
		return reportPage;
	}
	
	
}
