package com.easyfix.invoice.delegate;

import java.util.List;
import java.util.Map;

import com.easyfix.invoice.model.Invoice;

public interface InvoiceService {

	List<Invoice> clientInvoiceList() throws Exception;

	List<Invoice> clientInvoiceListByclientId(int clientId) throws Exception;

	Map<String, List<Invoice>> filterInvoiceListByClient(List<Invoice> invoiceList) throws Exception;

	void changeInvoiceStatus(int invId) throws Exception;

	void saveInvoicePaidAmount(int invId, float paidAmount, int paidBy) throws Exception;
	List<Invoice> clientInvoiceListHibernate() throws Exception;
	Invoice getInvoiceDetailById(Invoice inv) throws Exception;
	void modifyJobServiceFromInvoice(Invoice inv) throws Exception;

}
