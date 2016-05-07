/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado').config(trocadoRoutes);

    function trocadoRoutes( $urlRouterProvider) {
        $urlRouterProvider.when('', '/expenses');
    }
}(angular));
