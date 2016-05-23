/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado', [
        'ng', 'ngMaterial', 'ui.router',
        'trocado.expenses', 'trocado.templates',
        'account.endpoint', 'expense.endpoint',
        'upload.endpoint', 'trocado.services'
    ]);

    angular.module('trocado').config(configTrocado);
    angular.module('trocado').run(runTrocado);

    function configTrocado($mdThemingProvider, ExpenseProvider, UploadProvider) {
        $mdThemingProvider.theme('default')
            .primaryPalette('deep-purple')
            .accentPalette('pink');

        UploadProvider.apiRoot('/_ah/api/upload/v1/');
        ExpenseProvider.apiRoot('/_ah/api/expense/v1/');
    }

    function runTrocado($log) {
        $log.debug('Meus Trocados!');
    }

}(angular));
