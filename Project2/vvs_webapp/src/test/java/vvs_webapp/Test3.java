package vvs_webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

public class Test3 {
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
	public void setup() throws MalformedURLException, IOException {
		 vat = TestUtils.addCustomer(page);
	}
	
	/*
	 * a new sale will be listed as an open sale for the respective customer;
	 */
	@Test
	public void test3() throws IOException {
		
		final int indexOfStatus = 3;
		
		//test
		HtmlAnchor addSaleLink = page.getAnchorByHref("addSale.html");
		HtmlPage nextPage = (HtmlPage) addSaleLink.openLinkInNewWindow();
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
        
        assertEquals("not in the correct page","Sales Info", reportPage.getTitleText());
        
        Object[] tables = reportPage.getByXPath("//table").toArray();
        
        if (tables.length == 0) {
			fail();
		}
        HtmlTable table = (HtmlTable) tables[0];
        
        assertEquals("O",table.getRows().get(table.getRows().size()-1).getCell(indexOfStatus).asText());
        
	}
	
	@After
	public void teardown() throws MalformedURLException, IOException {
		 //remove client
        TestUtils.remove(page,vat);
	}
	
	
	

}
