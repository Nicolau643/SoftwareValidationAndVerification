package vvs_dbsetup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static vvs_dbsetup.DBSetupUtils.DB_PASSWORD;
import static vvs_dbsetup.DBSetupUtils.DB_URL;
import static vvs_dbsetup.DBSetupUtils.DB_USERNAME;
import static vvs_dbsetup.DBSetupUtils.DELETE_ALL;
import static vvs_dbsetup.DBSetupUtils.INSERT_ADRESSES;
import static vvs_dbsetup.DBSetupUtils.INSERT_CUSTOMER_SALE_DATA;
import static vvs_dbsetup.DBSetupUtils.startApplicationDatabaseForTesting;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import webapp.services.ApplicationException;
import webapp.services.CustomerDTO;
import webapp.services.CustomerService;
import webapp.services.CustomersDTO;
import webapp.services.SaleDTO;
import webapp.services.SaleService;

public class AllTests {
	
	private static Destination dataSource;
	
    // the tracker is static because JUnit uses a separate Test instance for every test method.
    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
   @BeforeClass
    public static void setupClass() {
    	startApplicationDatabaseForTesting();
		dataSource = DriverManagerDestination.with(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
	@Before
	public void setup() throws SQLException {

		Operation initDBOperations = Operations.sequenceOf(
			  DELETE_ALL
			, INSERT_CUSTOMER_SALE_DATA
			, INSERT_ADRESSES
			);
		
		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);
		
        // Use the tracker to launch the DbSetup. This will speed-up tests 
		// that do not not change the BD. Otherwise, just use dbSetup.launch();
        dbSetupTracker.launchIfNecessary(dbSetup);
		
	}
	/*
	private static CustomerDTO c;
	private static SaleDTO s;
	
	@BeforeAll
	public void beforeAll() throws ApplicationException {
		c = CustomerService.INSTANCE.getAllCustomers().customers.get(0);
		s = SaleService.INSTANCE.getAllSales().sales.get(0);
	}
	*/
	@Test
	public void test1() throws ApplicationException {
		
		CustomersDTO cs = CustomerService.INSTANCE.getAllCustomers();
		int numcustomers_init = cs.customers.size();
		
		CustomerDTO c = cs.customers.get(0);
		
		try {
			CustomerService.INSTANCE.addCustomer(c.vat, c.designation, c.phoneNumber);
		} catch (Exception e) {
			assertEquals(numcustomers_init,CustomerService.INSTANCE.getAllCustomers().customers.size());
			return;
		}
		
		fail("Customer added!");
		
	}
	
	@Test
	public void test2() throws ApplicationException {
		
		int contact = 987654321;
		
		CustomerDTO c = CustomerService.INSTANCE.getAllCustomers().customers.get(0);
		
		CustomerService.INSTANCE.updateCustomerPhone(c.vat, contact);
		
		c = CustomerService.INSTANCE.getCustomerByVat(c.vat);
		
		assertEquals(contact,c.phoneNumber);
		
		
	}
	
	@Test
	public void test3() throws ApplicationException {
		CustomersDTO cs = CustomerService.INSTANCE.getAllCustomers();
		
		for (CustomerDTO c : cs.customers) {
			CustomerService.INSTANCE.removeCustomer(c.vat);
		}
		
		assertEquals(0,CustomerService.INSTANCE.getAllCustomers().customers.size());

	}
	

	@Test
	public void test4() throws ApplicationException {
		CustomerDTO c = CustomerService.INSTANCE.getAllCustomers().customers.get(0);
		
		CustomerService.INSTANCE.removeCustomer(c.vat);
		
		try {
			CustomerService.INSTANCE.addCustomer(c.vat,c.designation,c.phoneNumber);
		} catch (ApplicationException e) {
			fail("Ocorreu Exception!");
		}
	}
	

	@Test
	public void test5() throws ApplicationException {
		
		CustomerDTO c = CustomerService.INSTANCE.getAllCustomers().customers.get(0);
		
		CustomerService.INSTANCE.removeCustomer(c.vat);
		
		assertEquals(0,SaleService.INSTANCE.getSaleByCustomerVat(c.vat).sales.size());
		
	}
	
	@Test
	public void test6() throws ApplicationException {
		
		int sales_size = SaleService.INSTANCE.getAllSales().sales.size();
		
		CustomerDTO c = CustomerService.INSTANCE.getAllCustomers().customers.get(0);
		
		SaleService.INSTANCE.addSale(c.vat);
		
		assertEquals(sales_size+1,SaleService.INSTANCE.getAllSales().sales.size());
		
	}
	
	/*
	 * Adding a sale of an existing costumer increases by one the sales of that costumer
	 */
	@Test
	public void test7ExtraSale() throws ApplicationException {
		
		CustomerDTO c = CustomerService.INSTANCE.getAllCustomers().customers.get(0);
		
		int sales_init = SaleService.INSTANCE.getSaleByCustomerVat(c.vat).sales.size();
		
		SaleService.INSTANCE.addSale(c.vat);
		
		assertEquals(sales_init+1, SaleService.INSTANCE.getSaleByCustomerVat(c.vat).sales.size());
		
	}
	
	/*
	 * When close a closed sale, it keeps closed
	 */
	@Test
	public void test8ExtraSale() throws ApplicationException {
		
		SaleDTO s = SaleService.INSTANCE.getAllSales().sales.get(0);
		
		SaleService.INSTANCE.updateSale(s.id);
		
		SaleService.INSTANCE.updateSale(s.id);
		
		
		assertEquals("C", SaleService.INSTANCE.getAllSales().sales.get(0).statusId);
		
	}
	
	/*
	 * When a sale is removed(happens when  the customer of the sale is removed),
	 *  the corresponding sale delivery should be removed too
	 */
	@Test
	public void test9ExtraDeliverySale() throws ApplicationException {
		
		SaleDTO s = SaleService.INSTANCE.getAllSales().sales.get(0);
		
		SaleService.INSTANCE.addSaleDelivery(s.id, CustomerService.INSTANCE.getAllAddresses(s.customerVat).addrs.get(0).id);
		
		CustomerService.INSTANCE.removeCustomer(s.customerVat);
		
		assertEquals(0,SaleService.INSTANCE.getSalesDeliveryByVat(s.customerVat).sales_delivery.size());
		
	}
	
	/*
	 * Adding a delivery sale to a customer, increases by one the number of 
	 * delivery sales of the same customer
	 */
	@Test
	public void test10ExtraDeliverySale() throws ApplicationException {
		SaleDTO s = SaleService.INSTANCE.getAllSales().sales.get(0);
		
		int numberDelivSales = SaleService.INSTANCE.getSalesDeliveryByVat(s.customerVat).sales_delivery.size();
		
		SaleService.INSTANCE.addSaleDelivery(s.id, CustomerService.INSTANCE.getAllAddresses(s.customerVat).addrs.get(0).id);
		
		assertEquals(numberDelivSales+1,SaleService.INSTANCE.getSalesDeliveryByVat(s.customerVat).sales_delivery.size());
		
	}

}
