/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.expenses').controller('ExpensesHomeController', ExpensesHomeController);

    var counter = 198;
    var BATCH_SIZE = 50;

    function ExpensesHomeController($rootScope, $state, $timeout, Dialog, Expense, expenses, DateUtil, ToolbarEvents) {
        var vm = this;
        vm.expenses = expenses.data.items;
        vm.virtualItems = new VirtualRepeatModel(Expense);

        vm.getMatches = getMatches;
        vm.selected = selected;
        vm.viewExpense = viewExpense;
        vm.deleteExpense = deleteExpense;
        vm.refresh = refresh;

        vm.selectedItem = undefined;
        vm.searchText = '';

        vm.sections = [];
        vm.sectionExpenses = {};

        vm.refresh();

        $rootScope.$on(ToolbarEvents.Refresh, vm.refresh);

        function VirtualRepeatModel() {
            this.cache = {start: 0, end: 0, max: BATCH_SIZE * 2, items: []};
        }

        VirtualRepeatModel.prototype.load = function (index) {
            //TODO: unload when cache is too big
            var end = index + BATCH_SIZE;
            this.cache.end = end;
            for (var i = index; i < end; ++i) {
                this.cache.items[i] = null;
            }
            Expense.query(BATCH_SIZE, index).then(ok, fail);
            var that = this;

            function ok(r) {
                var expenses = r.data.items;

                var last = index;
                for (var i = 0; i < expenses.length; ++i) {
                    last = i + index;
                    that.cache.items[last] = expenses[i];
                }
                if (expenses.length < BATCH_SIZE) {
                    if (!that.cache.total) that.cache.total = index + expenses.length;
                    that.cache.total = Math.min(that.cache.total, index + expenses.length);
                    that.cache.max = that.cache.total;
                } else {
                    that.cache.max = Math.max(that.cache.max, last + 2);
                }
            }

            function fail(r) {
                Dialog.showAlert('Refresh failed', 'Something went wrong...');
            }

        };

        VirtualRepeatModel.prototype.getItemAtIndex = function (index) {
            if (index < 0) return null;
            if (!(index >= this.cache.start && index < this.cache.end)) this.load(index);
            return this.cache.items[index - this.cache.start];
        };

        VirtualRepeatModel.prototype.getLength = function () {
            return this.cache.max;
        };


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
