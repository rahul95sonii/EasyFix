var TableManaged2 = function () {

    var initTable1 = function () {

        var table = $('#sample_1');

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
                "orderable": false
            }, {
                "orderable": false
            },{
                "orderable": false
            }
            , {
                "orderable": false
            }
            , {
                "orderable": false
            }
            , {
                "orderable": false
            }],


            "lengthMenu": [
                [5],
                [5] // change per page values here
            ],
            // set the initial value
            "pageLength": 5
        });

        var tableWrapper = jQuery('#sample_1_wrapper');

       
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper.find('.dataTables_length').hide();
        //tableWrapper.find('.dataTables_filter').hide();
    }

   

    return {

        //main function to initiate the module
        init: function () {
            if (!jQuery().dataTable) {
                return;
            }

            initTable1();
        }

    };

}();