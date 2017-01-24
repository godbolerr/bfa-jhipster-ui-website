(function() {
    'use strict';
    angular
        .module('bfalegacyApp')
        .factory('SearchFlight', SearchFlight);

    SearchFlight.$inject = ['$resource'];

    function SearchFlight ($resource) {
        var resourceUrl =  'api/search-flights/:id';

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
