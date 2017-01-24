(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('passengerpAppSuffix', {
            parent: 'entity',
            url: '/passengerpAppSuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Passengers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/passenger/passengerspAppSuffix.html',
                    controller: 'PassengerPAppSuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('passengerpAppSuffix-detail', {
            parent: 'entity',
            url: '/passengerpAppSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Passenger'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/passenger/passengerpAppSuffix-detail.html',
                    controller: 'PassengerPAppSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Passenger', function($stateParams, Passenger) {
                    return Passenger.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'passengerpAppSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('passengerpAppSuffix-detail.edit', {
            parent: 'passengerpAppSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passenger/passengerpAppSuffix-dialog.html',
                    controller: 'PassengerPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Passenger', function(Passenger) {
                            return Passenger.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('passengerpAppSuffix.new', {
            parent: 'passengerpAppSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passenger/passengerpAppSuffix-dialog.html',
                    controller: 'PassengerPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                gender: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('passengerpAppSuffix', null, { reload: 'passengerpAppSuffix' });
                }, function() {
                    $state.go('passengerpAppSuffix');
                });
            }]
        })
        .state('passengerpAppSuffix.edit', {
            parent: 'passengerpAppSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passenger/passengerpAppSuffix-dialog.html',
                    controller: 'PassengerPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Passenger', function(Passenger) {
                            return Passenger.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('passengerpAppSuffix', null, { reload: 'passengerpAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('passengerpAppSuffix.delete', {
            parent: 'passengerpAppSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passenger/passengerpAppSuffix-delete-dialog.html',
                    controller: 'PassengerPAppSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Passenger', function(Passenger) {
                            return Passenger.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('passengerpAppSuffix', null, { reload: 'passengerpAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
