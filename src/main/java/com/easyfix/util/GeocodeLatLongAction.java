package com.easyfix.util;

import java.util.List;


import com.easyfix.customers.model.Address;
import com.easyfix.geocode.latlong.Latlong;
import com.opensymphony.xwork2.ModelDriven;

public class GeocodeLatLongAction extends CommonAbstractAction implements ModelDriven<Address>{

	private static final long serialVersionUID = 1L;

		private Latlong latLong;
		private Address addressObj;
	//	private Map<String, String> add;
		private List<Address> addList;
		
		@Override
		public Address getModel() {
			return setAddressObj(new Address());
		}
		

		public String chooseLatlong() throws Exception {
			
			try {
				String fullAddress = addressObj.getAddress();
				addList = latLong.getLatLong(fullAddress);
				
			} catch (Exception e) {
				System.out.println(e);
			}
			
			
			return SUCCESS;
		}
		
	
		
		
		public Latlong getLatLong() {
			return latLong;
		}


		public void setLatLong(Latlong latLong) {
			this.latLong = latLong;
		}


		public Address getAddressObj() {
			return addressObj;
		}


		public Address setAddressObj(Address addressObj) {
			this.addressObj = addressObj;
			return addressObj;
		}

/*
		public Map<String, String> getAdd() {
			return add;
		}


		public void setAdd(Map<String, String> add) {
			this.add = add;
		}

*/
		public List<Address> getAddList() {
			return addList;
		}


		public void setAddList(List<Address> addList) {
			this.addList = addList;
		}

		
	}
