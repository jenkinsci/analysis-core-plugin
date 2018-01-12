(function ($) {
    var highPriority = $('#HIGH').text();
    var normalPriority = $('#NORMAL').text();
    var lowPriority = $('#LOW').text();

    var canvas = document.getElementById("doughnut-priorities-summary");
    var ctx = canvas.getContext("2d");
    ctx.height = 200;
    ctx.width = 200;
    var prioritiesSummaryChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ["High", "Normal", "Low"],
            datasets: [{
                label: 'High priority, Normal priority, Low priority',
                data: [highPriority, normalPriority, lowPriority],
                backgroundColor: [
                    '#f5c6cb',
                    '#ffeeba',
                    '#b8daff'
                ],
                hoverBackgroundColor: [
                    '#f5929f',
                    '#ffeb75',
                    '#53bdff'
                ],
                hoverBorderColor: [
                    '#fff', '#fff', '#fff'
                ]
            }]
        }
    });

    canvas.onclick = function (evt) {
        var activePoints = prioritiesSummaryChart.getElementsAtEvent(evt);
        if (activePoints[0]) {
            var chartData = activePoints[0]['_chart'].config.data;
            var idx = activePoints[0]['_index'];

            var url = chartData.labels[idx];
            window.open(url, "_self");
        }
    };

    var newWarning = $('#numberOfNewWarnings').text();
    var fixedWarning = $('#numberOfFixedWarnings').text();

    var canvas2 = document.getElementById("doughnut-trend-summary");
    var ctx2 = canvas2.getContext("2d");
    ctx2.height = 200;
    ctx2.width = 200;
    var trendSummaryChart = new Chart(ctx2, {
        type: 'doughnut',
        data: {
            labels: ["New", "Fixed"],
            datasets: [{
                label: 'New warnings, Fixed warnings',
                data: [newWarning, fixedWarning],
                backgroundColor: [
                    '#f5c6cb',
                    '#b8daff'
                ],
                hoverBackgroundColor: [
                    '#f5929f',
                    '#53bdff'
                ],
                hoverBorderColor: [
                    '#fff', '#fff'
                ]
            }]
        }
    });

    canvas2.onclick = function (evt) {
        var activePoints = trendSummaryChart.getElementsAtEvent(evt);
        if (activePoints[0]) {
            var chartData = activePoints[0]['_chart'].config.data;
            var idx = activePoints[0]['_index'];

            var url = chartData.labels[idx];
            window.open(url, "_self");
        }
    };
})(jQuery);

// .getElementsAtEvent(e)
//
// Looks for the element under the event point, then returns all elements at
// the same data index. This is used internally for 'label' mode highlighting.
//
// Calling getElementsAtEvent(event) on your Chart instance passing an
// argument of an event, or jQuery event, will return the point elements
// that are at that the same position of that event.
