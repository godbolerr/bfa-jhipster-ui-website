(function() {
    'use strict';
    angular
        .module('bfalegacyApp')
        .factory('Inventory', Inventory);

    Inventory.$inject = ['$resource'];

    function Inventory ($resource) {
        var resourceUrl =  'api/inventories/:id';

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
