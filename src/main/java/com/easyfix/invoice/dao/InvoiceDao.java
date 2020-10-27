package com.easyfix.invoice.dao;

import java.util.List;

import com.easyfix.invoice.model.Invoice;

public interface InvoiceDao {
	
	List<Invoice> clientInvoiceList() throws Exception;

	List<Invoice> clientInvoiceListByclientId(int clientId) throws Exception;

	void changeInvoiceStatus(int invId) throws Exception;

	void saveInvoicePaidAmount(int invId, float paidAmount, int paidBy) throws Exception;
	Invoice getInvoiceDetailById(Invoice inv) throws Exception;
}
