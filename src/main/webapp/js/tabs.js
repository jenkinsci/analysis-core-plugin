(function ($) {
    // Select first tab
    $('#tab-details').find('li:first-child a').tab('show');

    // Save element href attribute value locally to the users browser (HTML5 localStorage object)
    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
        localStorage.setItem('activeTab', $(e.target).attr('href'));
    });

    // Activate saved Tab if attribute is set
    var activeTab = localStorage.getItem('activeTab');
    if (activeTab) {
        $('#tab-details').find('a[href="' + activeTab + '"]').tab('show');
    }
})(jQuery);