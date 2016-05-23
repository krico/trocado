/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado').controller('ApplicationController', ApplicationController);

    function ApplicationController($log, $rootScope, Dialog) {
        var app = this;

        $rootScope.$on('$stateNotFound', onStateNotFound);

        function onStateNotFound(event, unfoundState, fromState, fromParams) {
            $log.debug(unfoundState.to);
            $log.debug(unfoundState.toParams);
            $log.debug(unfoundState.options);
            Dialog.showAlert('State not found "' + unfoundState.to + '"',
                'We are unable to navigate to this state', event);
        }
    }

}(angular));
