/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.services').service('DateUtil', DateUtil);

    var ONE_DAY = 1000 * 60 * 60 * 24;

    function DateUtil($filter) {
        this.$filter = $filter;
    }

    DateUtil.prototype.daysBetween = function (d1, d2) {
        var millis1 = d1.getTime();
        var millis2 = d2.getTime();
        return Math.round(Math.abs((millis1 - millis2) / (ONE_DAY)));
    };

    DateUtil.prototype.humanReadableSinceToday = function (date) {
        var now = new Date();
        var days = this.daysBetween(now, date);
        if (days == 0) {
            return 'today';
        }
        var after = date.getTime() > now.getTime();

        if (days == 1) {
            return after ? 'tomorrow' : 'yesterday';
        }
        if (days < 7) {
            return after ? 'in ' + days + ' days' : days + ' days ago';
        }

        if (days < 14) {
            return after ? 'in a week' : 'a week ago';
        }
        if (days < 21) {
            return after ? 'in 2 weeks' : '2 weeks ago';
        }

        if (days < 28) {
            return after ? 'in 3 weeks' : '3 weeks ago';
        }

        var format = 'MMM';
        if (days > 200) format = 'YYYY';
        return this.$filter('date')(date, format);
    };

}(angular));
