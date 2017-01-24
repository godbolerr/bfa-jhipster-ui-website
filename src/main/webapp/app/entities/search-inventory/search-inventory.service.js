(function() {
    'use strict';
    angular
        .module('bfalegacyApp')
        .factory('SearchInventory', SearchInventory);

    SearchInventory.$inject = ['$resource'];

    function SearchInventory ($resource) {
        var resourceUrl =  'api/search-inventories/:id';

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
