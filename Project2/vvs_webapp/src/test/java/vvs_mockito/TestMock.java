package vvs_mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import webapp.services.ApplicationException;
import webapp.services.CustomerDTO;

public class TestMock {

	@Test
	public void test1() throws ApplicationException {
		
		final int vat = 123456789;
		final String desig = "Marta";
		final int phone = 986473624;
			
		CostumerServiceClass csc = mock(CostumerServiceClass.class);
		
		doNothing().when(csc).addCustomer(vat, desig, phone);
		
		when(csc.getCustomerByVat(vat)).thenReturn(new CustomerDTO(1, vat, desig, phone));
		
		csc.addCustomer(vat, desig, phone);
		
		CustomerDTO res = csc.getCustomerByVat(vat);
			
		assertEquals(true,res.id==1 && 
						  res.vat==vat && 
						  res.designation.equals(desig) && 
						  res.phoneNumber==phone);
	
	}
	
}
