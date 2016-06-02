/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado').controller('ToolbarController', ToolbarController);

    function ToolbarController($rootScope, $mdMedia, $mdDialog, $document, ToolbarEvents) {
        var tool = this;
        tool.upload = upload;
        tool.refresh = refresh;

        function refresh(ev) {
            return $rootScope.$broadcast(ToolbarEvents.Refresh, ev);
        }

        function upload(ev) {
            return $mdDialog.show({
                controller: 'UploadDialogController',
                controllerAs: 'vm',
                templateUrl: 'expenses/expenses.upload-dialog.html',
                parent: angular.element($document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $mdMedia('sm') || $mdMedia('xs')
            }).then(ok, cancel);

            function ok(data) {
            }

            function cancel() {
            }
        }
    }

}(angular));
