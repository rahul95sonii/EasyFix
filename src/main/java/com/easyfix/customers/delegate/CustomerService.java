package com.easyfix.customers.delegate;

import java.util.List;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;

public interface CustomerService {
	
	public List<Customers> getCustomerList() throws Exception;	
	public int addUpdateCustomer(Customers customerObj, int userId) throws Exception;
	public Customers getCustomerDetailsById(int customerId) throws Exception;
	public Customers getCustomerDetailsByMobileNo(String mobileNo) throws Exception;
	public List<Address> getAddressList(int customerId) throws Exception;
	public Customers getCustomerAddressDetailsById(int addressId) throws Exception;	
	public int addUpdateCustomerAddress(Customers customerObj, int userId) throws Exception;
	public Address getAddressDetailsById(int addressId) throws Exception;	
	public List<Customers> getnoListForBulkSMS() throws Exception;	
}
