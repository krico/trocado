/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.services').service('Dialog', Dialog);

    function Dialog($window, $mdDialog) {
        this.showAlert = function (title, text, event) {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element($window.document.querySelector('#content')))
                    .clickOutsideToClose(true)
                    .title(title)
                    .textContent(text)
                    .ariaLabel(title)
                    .ok('Got it!')
                    .targetEvent(event)
            );
        };
    }

}(angular));
