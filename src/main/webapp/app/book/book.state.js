(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('book', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/book/book.html',
                    controller: 'BookController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
