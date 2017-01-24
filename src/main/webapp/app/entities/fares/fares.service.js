(function() {
    'use strict';
    angular
        .module('bfalegacyApp')
        .factory('Fares', Fares);

    Fares.$inject = ['$resource'];

    function Fares ($resource) {
        var resourceUrl =  'api/fares/:id';

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
