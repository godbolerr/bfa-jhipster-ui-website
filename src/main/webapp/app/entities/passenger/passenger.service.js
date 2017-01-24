(function() {
    'use strict';
    angular
        .module('bfalegacyApp')
        .factory('Passenger', Passenger);

    Passenger.$inject = ['$resource'];

    function Passenger ($resource) {
        var resourceUrl =  'api/passengers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
