(function() {
    'use strict';
    angular
        .module('bfalegacyApp')
        .factory('CheckInRecord', CheckInRecord);

    CheckInRecord.$inject = ['$resource', 'DateUtils'];

    function CheckInRecord ($resource, DateUtils) {
        var resourceUrl =  'api/check-in-records/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.checkInTime = DateUtils.convertLocalDateFromServer(data.checkInTime);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.checkInTime = DateUtils.convertLocalDateToServer(copy.checkInTime);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.checkInTime = DateUtils.convertLocalDateToServer(copy.checkInTime);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
