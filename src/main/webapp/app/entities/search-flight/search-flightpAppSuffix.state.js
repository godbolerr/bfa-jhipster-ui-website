(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('search-flightpAppSuffix', {
            parent: 'entity',
            url: '/search-flightpAppSuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SearchFlights'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/search-flight/search-flightspAppSuffix.html',
                    controller: 'SearchFlightPAppSuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('search-flightpAppSuffix-detail', {
            parent: 'entity',
            url: '/search-flightpAppSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SearchFlight'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/search-flight/search-flightpAppSuffix-detail.html',
                    controller: 'SearchFlightPAppSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SearchFlight', function($stateParams, SearchFlight) {
                    return SearchFlight.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'search-flightpAppSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('search-flightpAppSuffix-detail.edit', {
            parent: 'search-flightpAppSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-flight/search-flightpAppSuffix-dialog.html',
                    controller: 'SearchFlightPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SearchFlight', function(SearchFlight) {
                            return SearchFlight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('search-flightpAppSuffix.new', {
            parent: 'search-flightpAppSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-flight/search-flightpAppSuffix-dialog.html',
                    controller: 'SearchFlightPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                flightNumber: null,
                                origin: null,
                                destination: null,
                                flightDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('search-flightpAppSuffix', null, { reload: 'search-flightpAppSuffix' });
                }, function() {
                    $state.go('search-flightpAppSuffix');
                });
            }]
        })
        .state('search-flightpAppSuffix.edit', {
            parent: 'search-flightpAppSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-flight/search-flightpAppSuffix-dialog.html',
                    controller: 'SearchFlightPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SearchFlight', function(SearchFlight) {
                            return SearchFlight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('search-flightpAppSuffix', null, { reload: 'search-flightpAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('search-flightpAppSuffix.delete', {
            parent: 'search-flightpAppSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-flight/search-flightpAppSuffix-delete-dialog.html',
                    controller: 'SearchFlightPAppSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SearchFlight', function(SearchFlight) {
                            return SearchFlight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('search-flightpAppSuffix', null, { reload: 'search-flightpAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
