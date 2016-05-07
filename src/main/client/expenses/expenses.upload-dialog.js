/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.expenses').controller('UploadDialogController', UploadDialogController);

    function UploadDialogController($log, $mdDialog, Upload) {
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
            $log.debug(vm.file);
            var reader = new FileReader();
            reader.onload = function (e) {
                uploadFile(vm.file, btoa(e.target.result));
            };
            reader.readAsBinaryString(vm.file);
        }

        function uploadFile(file, data) {
            return Upload.upload({
                name: file.name,
                size: file.size,
                type: file.type,
                lastModified: file.lastModified,
                base64Data: data
            }).then(ok, fail);

            function ok(r) {
                $log.debug('ok: ' + angular.toJson(r.data));
                $mdDialog.hide();
            }

            function fail(r) {
                $log.debug('fail: ' + angular.toJson(r));
            }
        }
    }

}(angular));
