/*global angular*/
(function (angular) {
    'use strict';


    /**
     * Constants for the toolbar related events
     */
    var ToolbarEvents = {
        Refresh: 'tool-refresh'
    };
    angular.module('trocado.constants')
        .constant('ToolbarEvents', ToolbarEvents)
        .run(runToolEvents);

    function runToolEvents($rootScope, $log, ToolbarEvents) {
        for (var eventName in ToolbarEvents) {
            if (ToolbarEvents.hasOwnProperty(eventName)) {
                $rootScope.$on(ToolbarEvents[eventName], mkListener(eventName));
            }
        }
        function mkListener(name) {
            return function (event) {
                $log.debug('ToolbarEvent.' + name + '=' + ToolbarEvents[name] + ': ' + angular.toJson(event));
            };
        }
    }
}(angular));
