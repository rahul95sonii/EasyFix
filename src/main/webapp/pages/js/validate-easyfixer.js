 			var form1 = $('#easyfixer_formId');
 			var error1 = $('.alert-danger', form1);
 			var success1 = $('.alert-success', form1);

            form1.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input
                messages: {
                    	
                	cityId: {
                             required: "Please select a City type"
                         },
                         
                         easyfixerType: {
                             required: "Please select an easyfixer type"
                         },
                        
                         serviceTypeId: {
                             required: "Please select  at least 1 type of Service",
                             maxlength: jQuery.validator.format("Max {0} items allowed for selection"),
                             minlength: jQuery.validator.format("At least {0} items must be selected"),
                         	}
                          },
                
                    rules: {
                    	
                    	easyfixerName: {
                    			minlength: 2,
                    			required: true
	                    },
	                    easyfixerNo: {
	                    	required: true
	                    },
	                    easyfixerBaseGps: {
	                    	required: true
	                    },
	                    
	                    easyfixerEmail: {
	                    	 email: true
	                    },
	                    easyfixerAddress: {
	                        required: true,
	                        minlength: 10
	                    },
	                    easyfixerCityId: {
	                    	required: true
	                    },
	                    easyfixerPin: {
	                        required: true,
	                        number: true
	                    },
	                    easyfixerType: {
	                        required: true,
	                    },
	                    easyfixerServiceType: {
	                        required: true,
	                        minlength: 1,
	                    }
	                   
	                },
                
                errorPlacement: function (error, element) { // render error placement for each input type
                if (element.parent(".input-group").size() > 0) {
                    error.insertAfter(element.parent(".input-group"));
                } else if (element.attr("data-error-container")) { 
                    error.appendTo(element.attr("data-error-container"));
                } else if (element.parents('.radio-list').size() > 0) { 
                    error.appendTo(element.parents('.radio-list').attr("data-error-container"));
                } else if (element.parents('.radio-inline').size() > 0) { 
                    error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
                } else if (element.parents('.checkbox-list').size() > 0) {
                    error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
                } else if (element.parents('.checkbox-inline').size() > 0) { 
                    error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
                } else {
                    error.insertAfter(element); // for other inputs, just perform default behavior
                }
                
                },

             
                invalidHandler: function (event, validator) { //display error alert on form submit              
                   success1.hide();
                   error1.show();
                   Metronic.scrollTo(error1, -200);
                },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    $(element)
                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
                },

                success: function (label) {
                    label
                        .closest('.form-group').removeClass('has-error'); // set success class to the control group
                },

                submitHandler: function (form) {
                   success1.show();
                   error1.hide();
                   form.submit();
                }
            });
            
            $('.select2_category', form1).change(function () {
            	form1.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
            });