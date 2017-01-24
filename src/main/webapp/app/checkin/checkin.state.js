(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('checkin', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/checkin/checkin.html',
                    controller: 'CheckinController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
