/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.expenses').controller('ExpensesHomeController', ExpensesHomeController);

    var counter = 198;
    var BATCH_SIZE = 50;

    function ExpensesHomeController($rootScope, $scope, $state, $timeout, $window, Dialog, Expense, expenses, Repeater,
                                    ToolbarEvents) {
        var vm = this;
        vm.expenses = expenses.data.items;
        vm.virtualItems = Repeater.forEndpoint(Expense);
        vm.getListHeight = getListHeight;

        vm.getMatches = getMatches;
        vm.selected = selected;
        vm.viewExpense = viewExpense;
        vm.deleteExpense = deleteExpense;
        vm.refresh = refresh;
        vm.onResize = onResize;

        vm.selectedItem = undefined;
        vm.searchText = '';

        vm.sections = [];
        vm.sectionExpenses = {};

        vm.refresh();

        $rootScope.$on(ToolbarEvents.Refresh, vm.refresh);

        $window.addEventListener('resize', vm.onResize);
        $scope.$on('$destroy', function () {
            $window.removeEventListener('resize', onResize);
        });

        function getListHeight() {
            return {height: '' + ($window.innerHeight - 72) + 'px'};
        }

        function onResize() {
            $scope.$digest();
        }

        $scope.$on('$destroy', function () {
            $window.removeEventListener('resize', onResize);
        });

        function refresh() {
            Expense.query().then(ok, fail);

            function ok(r) {
                vm.expenses = r.data.items;
            }

            function fail(r) {
                Dialog.showAlert('Refresh failed', 'Something went wrong...');
            }

        }

        function viewExpense($event, expense) {
            $timeout(function () {
                $state.go('expenses.detail', {id: expense.id, expense: expense})
            }, 200);
        }

        function deleteExpense($event, expense) {
            var idx = vm.expenses.indexOf(expense);
            vm.expenses.splice(idx, 1);
            Expense.delete(expense.id);
        }

        function selected() {
            if (vm.selectedItem && vm.selectedItem.description && vm.selectedItem.description != '') {
                Expense.save({
                    description: vm.selectedItem.description,
                    amount: vm.selectedItem.amount
                });

                vm.expenses.unshift(vm.selectedItem);
                vm.selectedItem = undefined;
                vm.searchText = '';
            }
        }

        var count = 0;

        function getMatches(text) {
            var item = it(text);
            var found = [item];
            var id = ++count;
            return Expense.query().then(ok, fail);

            function ok(r) {
                angular.forEach(r.data.items, function (v) {
                    if (v.description.indexOf(item.description) > -1) {
                        found.push(angular.copy(v));
                    }
                });
                return found;
            }

            function fail(r) {
                Dialog.showAlert('Request failed', 'Something went wrong...');
            }

        }


        function it(text) {
            var amount = 0.0;
            var groups = false;
            if (/* amt at start */ (groups = /^([0-9]+)([,.][0-9]+)?\s+(.*)$/.exec(text) )) {
                amount = amt(groups[1], groups[2]);
                text = groups[3];
            } else if (/* amt at end   */ (groups = /^(.*)\s+([0-9]+)([,.][0-9]+)?$/.exec(text))) {
                amount = amt(groups[2], groups[3]);
                text = groups[1];
            } else if (/* typing amt */ (groups = /^\s*([0-9]+)([,.][0-9]*)?\s*$/.exec(text))) {
                amount = amt(groups[1], groups[2]);
                text = '';
            }
            return {
                id: ++counter,
                amount: amount,
                description: text,
                date: new Date(),
                labels: [text]
            };

            function amt(v, dec) {
                if (dec) return parseFloat(v + '.' + dec.substring(1));
                return parseFloat(v);
            }
        }
    }
}(angular));
