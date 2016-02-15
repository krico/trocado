/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado').controller('ApplicationController', ApplicationController);

    function ApplicationController($log) {
        var app = this;
        app.expenses = [it('11.50 lunch'), it('5,20 Starbucks'), it('Wallmart 115.98'), it('Gas 31')];
        app.getMatches = getMatches;
        app.selected = selected;
        app.viewExpense = viewExpense;
        app.deleteExpense = deleteExpense;
        app.selectedItem = undefined;
        app.searchText = '';

        function viewExpense($event, item) {
            $log.debug('viewExpense: ' + angular.toJson($event));
            $log.debug('viewExpense: ' + angular.toJson(item));
        }

        function deleteExpense($event, item) {
            var idx = app.expenses.indexOf(item);
            app.expenses.splice(idx, 1);
        }

        function selected() {
            if (app.selectedItem && app.selectedItem.display && app.selectedItem.display != '') {
                app.expenses.unshift(app.selectedItem);
                app.selectedItem = undefined;
                app.searchText = '';
            }
        }

        function getMatches(text) {
            var item = it(text);
            var found = [item];
            if (item.display && item.display.length >= 1) {
                angular.forEach(app.expenses, function (v) {
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
