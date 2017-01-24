(function() {
    'use strict';
    angular
        .module('bfalegacyApp')
        .factory('SearchFares', SearchFares);

    SearchFares.$inject = ['$resource'];

    function SearchFares ($resource) {
        var resourceUrl =  'api/search-fares/:id';

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
