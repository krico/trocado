/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.expenses').controller('ExpensesDetailController', ExpensesDetailController);

    function ExpensesDetailController($log, $stateParams) {
        var vm = this;
        vm.id = $stateParams.id;
        vm.expense = $stateParams.expense;
        //TODO: load if null
        if (vm.expense === null) vm.expense = {
            "id": vm.id,
            "amount": 11.5,
            "display": "lunch",
            "created": new Date(),
            "labels": ["lunch"]
        };
        $log.debug('Detail controller: ' + vm.id + ' / ' + angular.toJson(vm.expense));
    }

}(angular));
