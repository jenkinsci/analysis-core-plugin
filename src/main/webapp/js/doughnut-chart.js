/**
 * @file Initialising doughnut-charts
 * @author Cornelia Christof <cchristo@hm.edu>
 */

(function ($) {
    /**
     *
     * @summary set variables for graph data
     * @type {*|HTMLElement}
     */
    var high = $('#HIGH');
    var normal = $('#NORMAL');
    var low = $('#LOW');
    var numberWarnings = $('#number-warnings');

    /**
     *
     * @summary rendering the priority summary doughnut chart
     * @link http://www.chartjs.org/docs/latest/charts/doughnut.html
     * @type doughnut
     */
    var canvasSummary = document.getElementById('doughnut-priorities-summary');
    var ctxSummary = canvasSummary.getContext('2d');
    ctxSummary.height = 200;
    ctxSummary.width = 200;
    var prioritiesSummaryChart = new Chart(ctxSummary, {
        type: 'doughnut',
        data: {
            labels: ['High', 'Normal', 'Low'],
            datasets: [{
                label: 'High priority, Normal priority, Low priority',
                data: [high.data('priority'), normal.data('priority'), low.data('priority')],
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

    /**
     *
     * @summary adding a on click event to the doughnut chart
     * @link http://www.chartjs.org/docs/latest/developers/api.html
     * @event click
     */
    canvasSummary.onclick = function (evt) {
        var activePoints = prioritiesSummaryChart.getElementsAtEvent(evt);
        if (activePoints[0]) {
            var chartData = activePoints[0]['_chart'].config.data;
            var idx = activePoints[0]['_index'];

            var url = chartData.labels[idx];
            window.open(url, '_self');
        }
    };

    /**
     *
     * @summary rendering the warnings trend doughnut chart
     * @link http://www.chartjs.org/docs/latest/charts/doughnut.html
     * @type doughnut
     */
    var canvasTrend = document.getElementById('doughnut-trend-summary');
    var ctxTrend = canvasTrend.getContext("2d");
    ctxTrend.height = 200;
    ctxTrend.width = 200;
    var trendSummaryChart = new Chart(ctxTrend, {
        type: 'doughnut',
        data: {
            labels: ['New', 'Fixed'],
            datasets: [{
                label: 'New warnings, Fixed warnings',
                data: [numberWarnings.data('number-of-new-warnings'), numberWarnings.data('number-fixed-warnings')],
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

    /**
     *
     * @summary adding a on click event to the doughnut chart
     * @link http://www.chartjs.org/docs/latest/developers/api.html
     * @event click
     */
    canvasTrend.onclick = function (evt) {
        var activePoints = trendSummaryChart.getElementsAtEvent(evt);
        if (activePoints[0]) {
            var chartData = activePoints[0]['_chart'].config.data;
            var idx = activePoints[0]['_index'];

            var url = chartData.labels[idx];
            window.open(url, '_self');
        }
    };
})(jQuery);