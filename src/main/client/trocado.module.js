/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado', [
        'ng', 'ngMaterial', 'ui.router',
        'trocado.expenses', 'trocado.templates',
        'account.endpoint', 'expense.endpoint'
    ]);

    angular.module('trocado').config(configTrocado);
    angular.module('trocado').run(runTrocado);

    function configTrocado($mdThemingProvider, $mdIconProvider) {
        $mdThemingProvider.theme('default')
            .primaryPalette('deep-purple')
            .accentPalette('pink');
    }

    function runTrocado($log) {
        $log.debug('Meus Trocados!');
    }

}(angular));
