/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.expenses').controller('UploadDialogController', UploadDialogController);

    function UploadDialogController($log, $rootScope, $mdDialog, $mdToast, FileUpload, Expense, ToolbarEvents) {
        var vm = this;
        vm.hide = hide;
        vm.cancel = cancel;
        vm.upload = upload;
        vm.file = null;

        function hide() {
            $mdDialog.hide();
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function upload() {
            return FileUpload.upload(vm.file).then(onUploadComplete, fail);

            function onUploadComplete(r) {
                $log.debug('onUploadComplete: ' + angular.toJson(r));
                return Expense.batch(r).then(ok, fail);
            }

            function ok(r) {
                var toast = $mdToast.simple()
                    .textContent('Upload complete!')
                    .action('dismiss')
                    .highlightAction(false);
//                    .position($scope.getToastPosition());

                $mdDialog.hide();
                $mdToast.show(toast);

                $rootScope.$broadcast(ToolbarEvents.Refresh);

                $log.debug('ok: ' + angular.toJson(r));
            }

            function fail(r) {
                $log.debug('fail: ' + angular.toJson(r));
            }
        }
    }

}(angular));
