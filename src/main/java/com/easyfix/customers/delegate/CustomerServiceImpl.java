package com.easyfix.customers.delegate;

import java.util.List;

import com.easyfix.customers.dao.CustomerDao;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;

public class CustomerServiceImpl implements CustomerService{
	
	private CustomerDao customerDao;

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public List<Customers> getCustomerList() throws Exception {
		return customerDao.getCustomerList();
	}

	@Override
	public int addUpdateCustomer(Customers customerObj, int userId) throws Exception {
		return customerDao.addUpdateCustomer(customerObj,userId);
	}

	@Override
	public Customers getCustomerDetailsById(int customerId) throws Exception {
		return customerDao.getCustomerDetailsById(customerId);
	}
	
	@Override
	public Customers getCustomerDetailsByMobileNo(String mobileNo)	throws Exception {
		return customerDao.getCustomerDetailsByMobileNo(mobileNo);
	}

	@Override
	public List<Address> getAddressList(int customerId) throws Exception {
		return customerDao.getAddressList(customerId);
	}

	@Override
	public Customers getCustomerAddressDetailsById(int addressId) throws Exception {
		return customerDao.getCustomerAddressDetailsById(addressId);
	}

	@Override
	public int addUpdateCustomerAddress(Customers customerObj, int userId) throws Exception {
		return customerDao.addUpdateCustomerAddress(customerObj,userId);
	}

	@Override
	public Address getAddressDetailsById(int addressId) throws Exception {
		return customerDao.getAddressDetailsById(addressId);
	}

	@Override
	public List<Customers> getnoListForBulkSMS() throws Exception {
		return customerDao.getnoListForBulkSMS();
	}

	
	

	

}
