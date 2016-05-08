/*global angular*/
(function (angular) {
    'use strict';
    angular.module('trocado.services').service('FileUpload', FileUpload);

    function FileUpload($log, $q, $timeout, Upload) {
        this.upload = function (file) {
            try {
                var deferred = $q.defer();
                var reader = new FileReader();

                reader.onload = function (e) {
                    var b64data = btoa(e.target.result);

                    $timeout(function () {
                        Upload.upload({
                            name: file.name,
                            size: file.size,
                            type: file.type,
                            lastModified: file.lastModified,
                            base64Data: b64data
                        }).then(ok, fail);

                        function ok(r) {
                            deferred.resolve(r.data);
                        }

                        function fail(r) {
                            deferred.reject(r);
                        }
                    });
                };

                reader.onerror = function (e) {
                    $timeout(function () {
                        deferred.reject(e);
                    });
                };

                reader.readAsBinaryString(file);
                return deferred.promise;
            } catch (e) {
                $log.error('Failed to upload...');
                $log.error(e);
                return $q.reject(e);
            }
        };
    }

}(angular));
