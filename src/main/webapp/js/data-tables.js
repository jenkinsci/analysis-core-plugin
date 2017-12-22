(function ($) {
    function format() {
        var warningMessage = $('#warning-message').text();
        var warningTooltip = $('#warning-toolTip').text();

        return '<div><strong>' + warningMessage + '</strong><br/>'
            + warningTooltip + '</div>';
    }

    $('#modules').DataTable({
        "order": [],
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false
        }]
    });
    $('#packages').DataTable({
        "order": [],
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false
        }]
    });
    $('#files').DataTable({
        "order": [],
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false
        }]
    });
    $('#categories').DataTable({
        "order": [],
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false
        }]
    });
    $('#authors').DataTable({
        "order": [],
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false
        }]
    });
    $('#types').DataTable({
        "order": [],
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false
        }]
    });
    $('#origin').DataTable({
        "order": [],
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false
        }]
    });
    var table = $('#warnings').DataTable({
        "order": [],
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false
        }]
    });

    // Add event listener for opening and closing details
    $('#warnings').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row
            row.child(format(tr.data('child-value'))).show();
            tr.addClass('shown');
        }
    });
})(jQuery);