
        var form1 = $('#cratecard_formId');
        var error1 = $('.alert-danger', form1);
        var success1 = $('.alert-success', form1);

        form1.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            
            messages: {
            	
            	serviceTypeId: {
                     required: "Please select a Service type"
                 	}
                   },
           
            rules: {
            	serviceTypeId: {
                    required: true
                },
                
                	clientRateCardName: {
                    minlength: 2,
                    required: true
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