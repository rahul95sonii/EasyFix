package com.easyfix.Jobs.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.easyfix.Jobs.model.JobImage;

public class JobImageDaoImpl implements JobImageDao{

	private SessionFactory sessionFactory;
	@Override
	public void addJobImage(JobImage Image){
		Session session =null;
		try{
				session =sessionFactory.openSession();
		session.save(Image);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(session!=null && session.isOpen())
				session.close();
		}
	}
	
	@Override
	public List<JobImage> getJobImage(JobImage jobImage) {
		List<JobImage> list = new ArrayList<JobImage>();
		Session session= null;
		try{
		 session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(JobImage.class);
		if(jobImage.getJobId()>0){
			cr.add(Restrictions.eq("jobId", jobImage.getJobId()));
		}
		if(jobImage.getImageId()>0){
			cr.add(Restrictions.eq("imageId", jobImage.getImageId()));
		}
		
		list = cr.list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if (session!=null && session.isOpen()) {
	            session.close();
	        }
		}
		return list;
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
