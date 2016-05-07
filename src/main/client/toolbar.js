/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado').controller('ToolbarController', ToolbarController);

    function ToolbarController($mdMedia, $mdDialog, $document) {
        var tool = this;
        tool.upload = upload;

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
