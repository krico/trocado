/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.expenses').config(expensesRoutes);

    function expensesRoutes($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.when('/expenses', '/expenses/home');

        $stateProvider
            .state('expenses', {
                url: '/expenses',
                abstract: true,
                templateUrl: 'expenses/expenses.html'
            })
            .state('expenses.home', {
                url: '/home',
                templateUrl: 'expenses/expenses.home.html',
                controller: 'ExpensesHomeController as vm',
                resolve: {
                    expenses: function (Expense) {
                        return Expense.query();
                    }
                }
            })
            .state('expenses.detail', {
                url: '/detail/{id:int}',
                params: {
                    id: {value: undefined},
                    expense: {value: null}
                },
                templateUrl: 'expenses/expenses.detail.html',
                controller: 'ExpensesDetailController as vm'
            })
        ;
    }
}(angular));
