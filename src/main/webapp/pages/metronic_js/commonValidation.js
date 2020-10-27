function numericMetronic(cmpId, divId) {
	var val=jQuery("#"+cmpId).val();
	val = val.replace(/[0-9]*/g, ""); 
	if(val != "") {
		document.getElementById(divId).setAttribute("class", "form-group has-error");
	} else {
		document.getElementById(divId).setAttribute("class", "form-group has-success");
	}
}

function isNumericMetronic(cmpId, divId) {
	var val=jQuery("#"+cmpId).val();
	val = val.replace(/[0-9]*/g, ""); 
	if(val != "") {
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return false;
	} else {
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
		document.getElementById(divId).setAttribute("class", "form-group");
		return true;
	}

}

//freelancer
function alphanumericNumericMetronic(cmpId, divId) {
	var val=jQuery("#"+cmpId).val();
	val = val.replace(/^[a-zA-Z0-9]+$/, "");
	if(val != ""){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;		
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
		return false;
	}
	return false;
	
}

function validImagePDFDocumentMetronic(cmpId, divId)
{
	var name=jQuery("#"+cmpId).val();	
	if(name!=0)
		{
	var namePart = name.split(".");	
	var ext = namePart[ namePart.length - 1 ];
	if(	ext == "jpg" || ext == "JPG" ||
	 	ext == "bmp" || ext == "BMP" || 
	 	ext == "jpeg" || ext == "JPEG" || 
	 	ext == "gif" || ext == "GIF" || 
	 	ext == "png" || ext == "PNG" || 
	 	ext == "pdf" || ext == "PDF")	 	
	return true;	
	else{
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
		//bootbox.alert("Invalid File format, Please select *.JPEG, *.JPG, *.GIF, *.PNG, *.PDF, *.BMP formats only");
		return false;
	}
		}
	return true;
}

function validImageFileMetronic(cmpId, divId)
{
	var name=jQuery("#"+cmpId).val();
	
	if(name!=0)
		{
		
			var namePart = name.split(".");		
			var ext = namePart[ namePart.length - 1 ];			
			if(	ext == "jpg" || ext == "JPG" ||
					ext == "bmp" || ext == "BMP" || 
					ext == "jpeg" || ext == "JPEG" || 
					ext == "gif" || ext == "GIF" || 
					ext == "png" || ext == "PNG")	 	
				return true;	
			else
			{
				bootbox.alert("Invalid Image format, Please select *.JPEG, *.JPG, *.GIF, *.PNG, *.BMP formats only");
				return false;
			}
		}
	return true;
}

//freelancer
function mobileMetronic(cmpId, divId) {
	var val=jQuery("#"+cmpId).val();
	var val1 = val.replace(/[0-9]*/g, ""); 
	if(val1 != "" || val.length != 10) {
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please enter a 10 digit Mobile No.");
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group has-success");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
		return false;
	}
	return false;
}

function emailMetronic(cmpId, divId) {
	var val=jQuery("#"+cmpId).val();
	if(!(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(val))){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please enter valid email id");
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return false;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group has-success");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
		return true;
	}
}

function decimalMetronic(cmpId, divId) {
	var val=jQuery("#"+cmpId).val();
	if(!(/^[-+]?[0-9]+(\.[0-9]+)?$/.test(val))) {
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return false;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
		return true;
	}
}

function blankVal_Metronic(fldId, lbl)
{
	var val=jQuery("#"+fldId).val();
	val = val.replace(/[ ]*/g, ""); 
	if(val == ""){
		bootbox.alert("Please enter " + lbl+".");
		jQuery("#"+fldId).focus();
		return false;		
	}

	return true;
}
//return true if blank
function isBlankMetronic(fldId, lbl){
	var val=jQuery("#"+fldId).val();
	val = val.replace(/[ ]*/g, ""); 
	
	if(val == ""){
		
		jQuery("#"+fldId).focus();
		return true;		
	}

	return false;
}

function blankValMetronic(cmpId, divId) {
	var val=jQuery("#"+cmpId).val();
	val = val.replace(/[ ]*/g, ""); 
	if(val == ""){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;		
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
	}

	return false;
}

function blankValMultiSelectMetronic(cmpId, divId) {
	var val=jQuery("#"+cmpId).val();
	if(!val){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;		
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
	}

	return false;
}

function validEmailMetronic(cmpId, divId) {
	var val=jQuery("#"+cmpId).val();
	if(val!="" && !(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(val))){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please enter valid email id");
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
		return false;
	}
	return false;
}


//ri


function isComboSelectedMetronic(cmpId, divId) {
	var comboValue=jQuery("#"+cmpId).val();
	 if(comboValue==null || comboValue=="" || comboValue==0){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block");
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;		
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
	}

	return false;
}

function isNumericMetronicLabel(cmpId, divId, labelId) {
	var val=jQuery("#"+cmpId).val();
	val = val.replace(/[0-9]*/g, ""); 
	if(val != "") {
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please enter only numeric value for "+$("#"+labelId).text());
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");		
	}
	return false;
}
//ri



function blankValMetronicLabel(cmpId, divId, labelId) {
	var val=jQuery("#"+cmpId).val();
	val = val.replace(/[ ]*/g, ""); 
	if(val == ""){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please enter "+$("#"+labelId).text());		
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
	}

	return false;
}


function isComboSelectedMetronicLabel(cmpId, divId, labelId) {
	var comboValue=jQuery("#"+cmpId).val();
	 if(comboValue==null || comboValue=="" || comboValue==0){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please select "+$("#"+labelId).text());
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;		
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
	}

	return false;
}



function blankCheckBoxRadioMetronic(cmpName, divId, msgId, label) {
	//var val=jQuery("#"+cmpId).val();
	
	if ($("input:radio[name="+cmpName+"]:checked").length == 0){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + msgId).text("Please choose "+label+" options");
		$("#" + msgId).css("display", "block");
		$("#" + divId).focus();
		return true;
		} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + msgId).css("display", "none");
	}

	return false;
}


function blankFileMetronicLabel(cmpId, divId, labelId) {
	var val=jQuery("#"+cmpId).val();
	val = val.replace(/[ ]*/g, ""); 
	if(val == ""){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().parent().parent().children(".help-block").text("Please enter "+$("#"+labelId).text());		
		$("#" + cmpId).parent().parent().parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().parent().parent().children(".help-block").css("display", "none");
	}

	return false;
}


function blankZeroValMetronicLabel(cmpId, divId, labelId) {
	var val=jQuery("#"+cmpId).val();
	val = val.replace(/[ ]*/g, ""); 
	if(val == "" || parseInt(val) <= 0){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please enter "+$("#"+labelId).text());		
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
	}

	return false;
}

function isNumericDecimalMetronicLabel(cmpId, divId, labelId) {
	var val=jQuery("#"+cmpId).val();
	val = val.replace(/^[0-9]*(\.[0-9]+)?$/, ""); 
	if(val != "") {
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please enter only numeric value for "+$("#"+labelId).text());
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");		
	}
	return false;
}


function isMobileFieldMetronic(cmpId, divId, labelId) {
	var val=jQuery("#"+cmpId).val();
	var val1 = val.replace(/[0-9]*/g, ""); 
	if(val == ""){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please enter "+$("#"+labelId).text());		
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;
	}	
	else if(val1 != "" || val.length != 10) {
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please enter a 10 digit "+$("#"+labelId).text());
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group has-success");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
		return false;
	}
	return false;
}


function checkEqualTextMetronic(fCmpId, sCmpId, divId, labelId) {
	var fval=jQuery("#"+fCmpId).val();
	var sval=jQuery("#"+sCmpId).val();
	val = val.replace(/[ ]*/g, ""); 
	if(val == ""){
		document.getElementById(divId).setAttribute("class", "form-group has-error");
		$("#" + cmpId).parent().children(".help-block").text("Please enter "+$("#"+labelId).text());		
		$("#" + cmpId).parent().children(".help-block").css("display", "block");
		$("#" + cmpId).focus();
		return true;
	} else {
		document.getElementById(divId).setAttribute("class", "form-group");
		$("#" + cmpId).parent().children(".help-block").css("display", "none");
	}

	return false;
}