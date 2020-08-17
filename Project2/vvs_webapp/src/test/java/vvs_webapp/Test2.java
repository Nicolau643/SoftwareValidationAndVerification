package vvs_webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class Test2 {

	
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
	
	private static String vat1;
	private static String vat2;
	
	/*
	 * insert two new customers and check if all the information is properly listed in 
	 * the List All Customers use case;
	 */
	@Test
	public void test2() throws IOException {
		
		vat1 = "123456789";
		final String designation1 = "Marta";
		final String phone1 = "987645264";
		
		vat2 = "503183504"; //VAT da FCUL
		final String designation2 = "Pedro";
		final String phone2 = "963745184";
		
		HtmlAnchor addCostumerLink = page.getAnchorByHref("addCustomer.html");
		
		HtmlPage nextPage = (HtmlPage) addCostumerLink.openLinkInNewWindow();
		
		assertEquals("not in the correct page","Enter Name", nextPage.getTitleText());
		
		HtmlForm addCostumerForm = nextPage.getForms().get(0);
		
		HtmlInput vatInput = addCostumerForm.getInputByName("vat");
		vatInput.setValueAttribute(vat1);
		HtmlInput desInput = addCostumerForm.getInputByName("designation");
		desInput.setValueAttribute(designation1);
		HtmlInput phoneInput = addCostumerForm.getInputByName("phone");
		phoneInput.setValueAttribute(phone1);
		
		HtmlInput submit = addCostumerForm.getInputByName("submit");
		submit.click();
		
		vatInput.setValueAttribute(vat2);
		desInput.setValueAttribute(designation2);
		phoneInput.setValueAttribute(phone2);
		
		submit.click();
		
		HtmlAnchor listCostumersLink = page.getAnchorByHref("GetAllCustomersPageController");
		nextPage = (HtmlPage) listCostumersLink.openLinkInNewWindow();
		
		int aux = 0;
		
		final HtmlTable table = nextPage.getHtmlElementById("clients");
		for (final HtmlTableRow row : table.getRows()) {
			List<HtmlTableCell> cells= row.getCells();
		    if (cells.get(0).asText().equals(designation1)) {
		    	if (!cells.get(1).asText().equals(phone1) || !cells.get(2).asText().equals(vat1)) {
					fail("not the same");
				}else {
					aux++;
				}
			}else if (row.getCells().get(0).asText().equals(designation2)) {
				if (!cells.get(1).asText().equals(phone2) || !cells.get(2).asText().equals(vat2)) {
					fail("not the same");
				}
				else {
					aux++;
				}
			}
		    
		}
		
		assertEquals((Integer)2,(Integer)aux);
		
		
		
	}
	
	@After
	public void teardown() throws MalformedURLException, IOException {
		//remove clients
		TestUtils.remove(page,vat1);
		TestUtils.remove(page,vat2);
	}
	

}
