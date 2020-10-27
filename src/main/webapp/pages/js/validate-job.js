 			var form1 = $('#job_formId');
           var error1 = $('.alert-danger', form1);
           var success1 = $('.alert-success', form1);

            form1.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input
                messages: {
                	
                	selectcity: {
                        required: "Please select a Client type"
                    	},
                    	
                	selctyp: {
                             required: "Please select a Client type"
                         	},
                         	
                   	selservtyp:  {
                        required: "Please select a Service type"
                 	},
                 	
                 	selser: {
                        required: "Please select  at least 1 type of Service"
                        	},
                 	
                 	jdtin:  {
                        required: "Please choose a Job Call Time"
                	}
                 	
                           },
                
                    rules: {
                    	
                    	jname: {
                    			minlength: 2,
                    			required: true
                    	},
                    	
                    	jno: {
                    	required: true,
                        number: true
                    	},
                    
                    	jemail: {
                    	 required: true,
                         email: true
                    	},
                    	
                    	selctyp: {
                        	required: true
                        	},
                        	
                        selservtyp: {
                           	required: true
                           	},
                           	
                        selectcity: {
                            required: true
                            },
                           	
                        selser: {
                            required: true
                            },
                            
                        jserdesc: {
                    		minlength: 2,
                    		required: true
                    		},
                    		
                    	jdtin: {
                            required: true
                            },
                    
                    	cadd: {
                        required: true,
                        minlength: 5
                    	},
                    
                    	gps: {
                        	required: true
                        },
                        	
                    	pin: {
                        required: true,
                        number: true
                    	}
                    },
                    
                    

              /*  errorPlacement: function (error, element) { // render error placement for each input type
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
                },*/
                
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