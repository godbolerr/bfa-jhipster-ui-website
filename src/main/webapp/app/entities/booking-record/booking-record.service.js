(function() {
    'use strict';
    angular
        .module('bfalegacyApp')
        .factory('BookingRecord', BookingRecord);

    BookingRecord.$inject = ['$resource', 'DateUtils'];

    function BookingRecord ($resource, DateUtils) {
        var resourceUrl =  'api/booking-records/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.bookingDate = DateUtils.convertLocalDateFromServer(data.bookingDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.bookingDate = DateUtils.convertLocalDateToServer(copy.bookingDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.bookingDate = DateUtils.convertLocalDateToServer(copy.bookingDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
