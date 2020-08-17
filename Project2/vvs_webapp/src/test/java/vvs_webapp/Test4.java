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
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public class Test4 {

	
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
	private static String id;
	
	@Before
	public void setup() throws MalformedURLException, IOException {
		 vat = TestUtils.addCustomer(page);
		 id = TestUtils.addSale(page,vat);
	}
	
	/*
	 * after closing a sale, it will be listed as closed;
	 */
	@Test
	public void test4() throws FailingHttpStatusCodeException, IOException {
		
		HtmlInput submit;
		HtmlPage reportPage;
		WebRequest req;
		WebClient webClient= new WebClient(BrowserVersion.getDefault());
		Object[] tables;
		HtmlTable table;
	
		req =new WebRequest(new java.net.URL(APPLICATION_URL+"UpdateSaleStatusPageControler"),
		                           HttpMethod.GET);
		
		reportPage = (HtmlPage) webClient.getPage(req);
		 
		assertEquals("not in the correct page","Enter Sale Id", reportPage.getTitleText());
		 
		HtmlForm idForm = reportPage.getForms().get(0);
		
		HtmlInput idInput = idForm.getInputByName("id");
		idInput.setValueAttribute(id);

		submit = idForm.getInputByValue("Close Sale");
		submit.click();
		
        String formData = String.format("id=%s", id);
                
        req = new WebRequest(new java.net.URL(APPLICATION_URL+"UpdateSaleStatusPageControler"),
                           HttpMethod.POST);
        req.setRequestBody(formData);
           
        reportPage = (HtmlPage) webClient.getPage(req);
        
        webClient.close();
		
		assertEquals("not in the correct page:","Enter Sale Id", reportPage.getTitleText());
		
		tables = reportPage.getByXPath("//table").toArray();
        
        if (tables.length == 0) {
			fail();
		}
        
        table = (HtmlTable) tables[0];
        
        assertEquals(true,table.getRow(table.getRowCount()-1).asText().contains("C"));
        
	}
	
	@After
	public void teardown() throws MalformedURLException, IOException {
		TestUtils.remove(page,vat);
	}

	
	
}
