var PaginateTable = function () {

    return {

        //main function to initiate the module
        init: function (tId, index) {
              var oTable = $('#' + tId + '').dataTable({
                "aLengthMenu": [
                    [5, 10, 20, -1],
                    [5, 10, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 10,
                
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': true,
                        'aTargets': [0], 
                        'bSortable': false, 
                        'aTargets' : [index]
                    }
                ]
            });

            jQuery('#' + tId + '_wrapper .dataTables_filter input').addClass("form-control input-medium input-inline"); // modify table search input
            jQuery('#' + tId + '_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
            jQuery('#' + tId + '_wrapper .dataTables_length select').select2({
                showSearchInput : true //hide search box with special css class
            }); // initialize select2 dropdown

        }

    };

} ();



var PaginateTable1 = function () {

    return {

        //main function to initiate the module
        init: function (tId, index) {
              var oTable = $('#' + tId + '').dataTable({
                "aLengthMenu": [
                    [5, 10, 20, -1],
                    [5, 10, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 10,
                
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aaSorting" : [[0,"desc"]],
                "aoColumnDefs": [{
                        'bSortable': true,
                        'aTargets': [0], 
                        'bSortable': false, 
                        'aTargets' : [index]
                    }
                ]
            });

            jQuery('#' + tId + '_wrapper .dataTables_filter input').addClass("form-control input-medium input-inline"); // modify table search input
            jQuery('#' + tId + '_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
            jQuery('#' + tId + '_wrapper .dataTables_length select').select2({
                showSearchInput : true //hide search box with special css class
            }); // initialize select2 dropdown

        }

    };

} ();


