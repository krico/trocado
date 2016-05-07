/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.expenses').controller('ExpensesHomeController', ExpensesHomeController);

    var counter = 198;

    function ExpensesHomeController($log, $state, $timeout) {
        var vm = this;
        vm.expenses = [it('11.50 lunch'), it('5,20 Starbucks'), it('Wallmart 115.98'), it('Gas 31')];
        vm.getMatches = getMatches;
        vm.selected = selected;
        vm.viewExpense = viewExpense;
        vm.deleteExpense = deleteExpense;
        vm.selectedItem = undefined;
        vm.searchText = '';

        function viewExpense($event, expense) {
            $timeout(function () {
                $state.go('expenses.detail', {id: expense.id, expense: expense})
            }, 200);
        }

        function deleteExpense($event, expense) {
            var idx = vm.expenses.indexOf(expense);
            vm.expenses.splice(idx, 1);
        }

        function selected() {
            if (vm.selectedItem && vm.selectedItem.display && vm.selectedItem.display != '') {
                vm.expenses.unshift(vm.selectedItem);
                vm.selectedItem = undefined;
                vm.searchText = '';
            }
        }

        function getMatches(text) {
            var item = it(text);
            var found = [item];
            if (item.display && item.display.length >= 1) {
                angular.forEach(vm.expenses, function (v) {
                    if (v.display.indexOf(item.display) > -1) {
                        found.push(angular.copy(v));
                    }
                });
            }

            $log.debug('found[' + found.length + '] = ' + angular.toJson(found));
            return found;

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
                display: text,
                created: new Date(),
                labels: [text]
            };
            function amt(v, dec) {
                if (dec) return parseFloat(v + '.' + dec.substring(1));
                return parseFloat(v);
            }
        }
    }
}(angular));
