function getGeoLocation()
{
	
	if(blankValMetronicLabel("address","add-divId","add-lableId"))
		return false;
	if(isComboSelectedMetronicLabel("cityId","city-divId","city-lableId"))
		return false;
	
	var address = $("#address").val();
	var city = $("#cityId option:selected").text();
	

		$("#geoBtnId").attr("href", "#address-portlet-config");
		
		jQuery("#address-modal-id").html("<center>Please wait...</center> ");
		
		jQuery.ajax({  		
			type: "POST",
	   		url: "GeocodeLatLong?address="+address+", "+city+", India",
	   		//url: "GeocodeLatLong?address="+address+", "+city,
	   		success: function(data) 
	   		{  	
	   			jQuery("#address-modal-id").html(" ");
			    jQuery("#address-modal-id").html(data);
	   		}
		});
	
		return false;
	

}