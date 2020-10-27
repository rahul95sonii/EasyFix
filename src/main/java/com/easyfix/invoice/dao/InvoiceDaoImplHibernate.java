package com.easyfix.invoice.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.easyfix.invoice.model.Invoice;

@EnableTransactionManagement
public class InvoiceDaoImplHibernate  implements InvoiceDao{

	private SessionFactory sessionFactory;
	
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Invoice> clientInvoiceList() throws Exception {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Invoice> clientInvoiceListByclientId(int clientId)
			throws Exception {
		List<Invoice> list = null;
		try{
			Session session=null;
		 //list = (List<Invoice>) getHibernateTemplate().find("from Invoice");
			try 
			{
				 session = sessionFactory.openSession();
			} 
			catch (HibernateException e) 
			{
			   System.out.println(e);
			}
			list=	session.createCriteria(Invoice.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(list);
		 return list;
	}

	@Override
	public void changeInvoiceStatus(int invId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveInvoicePaidAmount(int invId, float paidAmount, int paidBy)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Invoice getInvoiceDetailById(Invoice inv) throws Exception {
		Invoice invFromDb = null;
		Session	 session=null;
		try{
			session = sessionFactory.openSession();
			 Criteria cr = session.createCriteria(Invoice.class);
			 if(inv.getInvoiceId()!=0)
			 cr.add(Restrictions.eq("invoiceId", inv.getInvoiceId()));
			 
			 invFromDb=	(Invoice) cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if (session!=null && session.isOpen()) {
	            session.close();
	        }
		}
		return invFromDb;
	}

}
