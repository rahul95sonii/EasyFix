var JobComboSearch = function () {


    return {
        //main function to initiate the module
        init: function () {

            // use select2 dropdown instead of chosen as select2 works fine with bootstrap on responsive layouts.
            $('.select2_job_filter').select2({
            	allowClear: true
	        });
            
        }

    };

}();