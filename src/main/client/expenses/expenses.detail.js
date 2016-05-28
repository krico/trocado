/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.expenses').controller('ExpensesDetailController', ExpensesDetailController);

    function ExpensesDetailController($log, $stateParams, Expense, Dialog) {
        var vm = this;
        vm.id = $stateParams.id;
        vm.expense = $stateParams.expense;
        vm.init = init;

        vm.init();
        function init() {
            if (vm.expense === null) {
                Expense.get(vm.id).then(ok, fail);
            }

            function ok(r) {
                vm.expense = r.data;
            }

            function fail(r) {
                Dialog.showAlert('Failed to load', 'Failed to load expense id: ' + vm.id);
            }
        }
    }

}(angular));
