var TableManaged = function () {

	var initTable6 = function () {

        var table = $('#sample_6');

        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": " _MENU_ records",
                "paging": {
                    "previous": "Prev",
                    "next": "Next"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.
            
            "columns": [{
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": true
            }, {
                "orderable": false
            }, {
                "orderable": true,
                "visible": false,
                "searchable": false
            }, {
                "searchable": true
            }, {
                "orderable": false,
                "searchable": false
            }],


            "lengthMenu": [
                [5],
                [5] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "order": [
	              [5, "asc"]
	          ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_6_wrapper');

       
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper.find('.dataTables_length').hide();
        tableWrapper.find('.dataTables_filter').hide();
    }

	//========= for table 7 ==============
	
	var initTable7 = function () {

        var table = $('#sample_7');

        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": " _MENU_ records",
                "paging": {
                    "previous": "Prev",
                    "next": "Next"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.
            
            "columns": [{
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": false
            }, {
                "orderable": false
            }],


            "lengthMenu": [
                [5],
                [5] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "order": [
	              [5, "asc"]
	          ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_7_wrapper');

       
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper.find('.dataTables_length').hide();
        tableWrapper.find('.dataTables_filter').hide();
    }
	
	
//========= for table 5 ==============
    var initTable5 = function () {

        var table = $('#sample_5');

        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": " _MENU_ records",
                "paging": {
                    "previous": "Prev",
                    "next": "Next"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.
            
            "columns": [{
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": true
            }, {
                "orderable": false
            }, {
                "orderable": true,
                "visible": false,
                "searchable": false
            }, {
                "searchable": true
            }, {
                "orderable": false,
                "searchable": false
            }],


            "lengthMenu": [
                [5],
                [5] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "order": [
	              [6, "asc"]
	          ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_5_wrapper');

       
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper.find('.dataTables_length').hide();
        tableWrapper.find('.dataTables_filter').hide();
    }
  //========= for table 8 ==============
    var initTable8 = function () {

        var table = $('#sample_8');

        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": " _MENU_ records",
                "paging": {
                    "previous": "Prev",
                    "next": "Next"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.
            
            "columns": [{
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": true
            }, {
                "orderable": false
            }, {
                "orderable": true,
                "visible": false,
                "searchable": false
            }, {
                "searchable": true
            }, {
                "orderable": false,
                "searchable": false
            }],


            "lengthMenu": [
                [5],
                [5] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "order": [
	              [6, "asc"]
	          ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_8_wrapper');

       
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper.find('.dataTables_length').hide();
        tableWrapper.find('.dataTables_filter').hide();
    }
//========= for table 2 ==============
    
    var initTable2 = function () {

        var table = $('#sample_2');

        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": " _MENU_ records",
                "paging": {
                    "previous": "Prev",
                    "next": "Next"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.
            
            "columns": [{
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": true
            }, {
                "orderable": false
            }, {
                "orderable": true,
                "visible": false,
                "searchable": false
            }, {
                "searchable": true
            }, {
                "orderable": false,
                "searchable": false
            }],


            "lengthMenu": [
                [5],
                [5] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "order": [
	              [7, "asc"]
	          ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_2_wrapper');

       
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper.find('.dataTables_length').hide();
        tableWrapper.find('.dataTables_filter').hide();
    }

//========= for table 3 ==============
    
    var initTable3 = function () {

        var table = $('#sample_3');

        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": " _MENU_ records",
                "paging": {
                    "previous": "Prev",
                    "next": "Next"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.
            
            "columns": [{
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": true
            }, {
                "orderable": false
            }, {
                "orderable": true,
                "visible": false,
                "searchable": false
            }, {
                "searchable": true
            }, {
                "orderable": false,
                "searchable": false
            }],


            "lengthMenu": [
                [5],
                [5] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "order": [
	              [7, "asc"]
	          ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_3_wrapper');

       
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper.find('.dataTables_length').hide();
        tableWrapper.find('.dataTables_filter').hide();
    } 
    
//========= for table 10 ==============
    
    var initTable10 = function () {

        var table = $('#sample_10');

        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": " _MENU_ records",
                "paging": {
                    "previous": "Prev",
                    "next": "Next"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.
            
            "columns": [{
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": true
            }, {
                "orderable": false
            }, {
                "orderable": true,
                "visible": false,
                "searchable": false
            }, {
                "searchable": true
            }, {
                "orderable": false,
                "searchable": false
            }],


            "lengthMenu": [
                [5],
                [5] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "order": [
	              [7, "asc"]
	          ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_10_wrapper');

       
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper.find('.dataTables_length').hide();
        tableWrapper.find('.dataTables_filter').hide();
    } 
//========= for table 2 ==============
    
    var initTable4 = function () {

        var table = $('#sample_4');

        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": " _MENU_ records",
                "paging": {
                    "previous": "Prev",
                    "next": "Next"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.
            
            "columns": [{
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": true
            }, {
                "orderable": false
            }, {
                "orderable": true,
                "visible": false,
                "searchable": false
            }, {
                "searchable": true
            }, {
                "orderable": false,
                "searchable": false
            }],


            "lengthMenu": [
                [5],
                [5] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "order": [
	              [7, "asc"]
	          ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_4_wrapper');

       
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper.find('.dataTables_length').hide();
        tableWrapper.find('.dataTables_filter').hide();
    }
    
//========= for table 15 ==============
    
    var initTable15 = function () {

        var table = $('#sample_15');

        table.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ records",
                "infoEmpty": "No records found",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": " _MENU_ records",
                "paging": {
                    "previous": "Prev",
                    "next": "Next"
                },
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.
            
            "columns": [{
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": false
            }, {
                "orderable": true
            }, {
                "orderable": false
            }, {
                "orderable": true,
                "visible": false,
                "searchable": false
            }, {
                "searchable": true
            }, {
                "orderable": false,
                "searchable": false
            }],


            "lengthMenu": [
                [5],
                [5] // change per page values here
            ],
            // set the initial value
            "pageLength": 5,
            "order": [
	              [7, "asc"]
	          ] // set first column as a default sort by asc
        });

        var tableWrapper = jQuery('#sample_15_wrapper');

       
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper.find('.dataTables_length').hide();
        tableWrapper.find('.dataTables_filter').hide();
    }



    return {

        //main function to initiate the module
        init: function () {
            if (!jQuery().dataTable) {
                return;
            }
            
            initTable2();
            initTable3();
            initTable4();
            initTable5();
            initTable6();
            initTable7();
            initTable8();
            initTable10();
            initTable15();
        }

    };

}();