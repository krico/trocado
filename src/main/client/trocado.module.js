/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado', [
        'ng', 'ngMaterial', 'ui.router',
        'trocado.constants', 'trocado.expenses', 'trocado.templates', 'trocado.services',
        'account.endpoint', 'expense.endpoint', 'upload.endpoint'
    ]);

    angular.module('trocado').config(configTrocado);
    angular.module('trocado').run(runTrocado);

    function configTrocado($mdThemingProvider, ExpenseProvider, UploadProvider) {
        $mdThemingProvider
            .theme('default')
            .primaryPalette('deep-purple')
            .accentPalette('pink')
            .backgroundPalette('grey');

        UploadProvider.apiRoot('/_ah/api/upload/v1/');
        ExpenseProvider.apiRoot('/_ah/api/expense/v1/');
    }

    function runTrocado($log) {
        $log.debug('Meus Trocados!');
    }

}(angular));
