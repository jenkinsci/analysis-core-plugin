/**
 * @file Initialising DataTables
 * @author Cornelia Christof <cchristo@hm.edu>
 */

(function ($) {
    /**
     *
     * @summary Formatting code, that is shown in warnings table, by clicking the details plus icon
     * @param message
     * @param tooltip
     * @link https://datatables.net/examples/api/row_details.html
     * @returns {string}
     */
    function format(message, tooltip) {
        return '<div><strong>' + message + '</strong><br/>' + tooltip + '</div>';
    }

    /**
     *
     * @summary Initialise DataTable functions on all tables with the class display
     * @link https://datatables.net/
     */
    $('table.display').DataTable({
        'pagingType': 'numbers', // Page number button only
        'columnDefs': [{
            'targets': 'no-sort', // Columns with class 'no-sort' are not orderable
            'orderable': false
        }]
    });

    /**
     *
     * @summary Set variable table for initialising DataTable functions on table #warnings
     */
    var table = $('#warnings').DataTable({
        'pagingType': 'numbers', // Page number button only
        'columnDefs': [{
            'targets': 'no-sort', // Columns with class 'no-sort' are not orderable
            'orderable': false
        }]
    });

    /**
     *
     * @summary Add event listener for opening and closing the warning details
     * @event click
     * @link https://datatables.net/examples/api/row_details.html
     */
    $('#warnings').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row
            row.child(format(tr.data('child-message'), tr.data('child-tooltip'))).show();
            tr.addClass('shown');
        }
    });
})(jQuery);