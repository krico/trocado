/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado', [
        'ng', 'ngMaterial', 'ui.router',
        'trocado.expenses', 'trocado.templates',
        'account.endpoint', 'expense.endpoint',
        'upload.endpoint'
    ]);

    angular.module('trocado').config(configTrocado);
    angular.module('trocado').run(runTrocado);

    function configTrocado($mdThemingProvider, $mdIconProvider, UploadProvider) {
        $mdThemingProvider.theme('default')
            .primaryPalette('deep-purple')
            .accentPalette('pink');

        UploadProvider.apiRoot('/_ah/api/upload/v1/');
    }

    function runTrocado($log) {
        $log.debug('Meus Trocados!');
    }

}(angular));
