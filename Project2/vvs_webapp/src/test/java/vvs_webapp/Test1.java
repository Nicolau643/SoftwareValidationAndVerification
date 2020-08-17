package vvs_webapp;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public class Test1 {
	
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
	
	@Before
	public void setup() throws IOException  {
		//add client
		vat = TestUtils.addCustomer(page);
	}
	
	@Test
	public void test1() throws IOException {
		
		final String adress1 = "fdsfds";
		final String door1 = "3";
		final String zip1 = "3333-333";
		final String location1 = "ee";
		
		final String adress2 = "fsdfds";
		final String door2 = "4";
		final String zip2 = "2222-222";
		final String location2 = "dff";
		
		int tableSizeBefore;
		int tableSizeAfter;
		
		
		// test
		tableSizeBefore = getSizeTable(vat);
		
		HtmlAnchor addAdressLink = page.getAnchorByHref("addAddressToCustomer.html");
		
		TestUtils.addAdress(vat, adress1, door1, zip1, location1, addAdressLink);
		
		TestUtils.addAdress(vat, adress2, door2, zip2, location2, addAdressLink);
		
		tableSizeAfter = getSizeTable(vat);
		
		assertEquals("not the same table size",tableSizeBefore+2,tableSizeAfter);
		
		
		
	}
	
	@After
	public void teardown() throws MalformedURLException, IOException {
		final String vat = "123456789";
		//remove client
		TestUtils.remove(page,vat);
		
	}

	private int getSizeTable(String vat) throws IOException {
		
		
		HtmlAnchor getCustomerByVATLink = page.getAnchorByHref("getCustomerByVAT.html");
		
		HtmlPage nextPage = (HtmlPage) getCustomerByVATLink.openLinkInNewWindow();
		assertEquals("not in the correct page","Enter Name", nextPage.getTitleText());
		
		HtmlPage reportPage;
        String formData = String.format("vat=%s", vat);
                
        WebRequest req =
            new WebRequest(new java.net.URL(APPLICATION_URL+"GetCustomerPageController"),
                           HttpMethod.POST);
        req.setRequestBody(formData);
        
        WebClient webClient = new WebClient(BrowserVersion.getDefault());
           
        reportPage = (HtmlPage) webClient.getPage(req);
        
        webClient.close();
		
		assertEquals("not in the correct page","Customer Info", reportPage.getTitleText());
		
		Object[] tables = reportPage.getByXPath("//table").toArray();
		int numberOfTable = tables.length;
		
		if (numberOfTable > 0) {
			HtmlTable infoTable = (HtmlTable) tables[0];
			
			return infoTable.getRowCount()-1;//-1 because the row that has the titles
		}
		
		return 0;
	}

}
