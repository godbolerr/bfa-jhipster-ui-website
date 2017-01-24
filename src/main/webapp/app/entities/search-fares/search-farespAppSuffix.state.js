(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('search-farespAppSuffix', {
            parent: 'entity',
            url: '/search-farespAppSuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SearchFares'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/search-fares/search-farespAppSuffix.html',
                    controller: 'SearchFaresPAppSuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('search-farespAppSuffix-detail', {
            parent: 'entity',
            url: '/search-farespAppSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SearchFares'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/search-fares/search-farespAppSuffix-detail.html',
                    controller: 'SearchFaresPAppSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SearchFares', function($stateParams, SearchFares) {
                    return SearchFares.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'search-farespAppSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('search-farespAppSuffix-detail.edit', {
            parent: 'search-farespAppSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-fares/search-farespAppSuffix-dialog.html',
                    controller: 'SearchFaresPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SearchFares', function(SearchFares) {
                            return SearchFares.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('search-farespAppSuffix.new', {
            parent: 'search-farespAppSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-fares/search-farespAppSuffix-dialog.html',
                    controller: 'SearchFaresPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fare: null,
                                currency: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('search-farespAppSuffix', null, { reload: 'search-farespAppSuffix' });
                }, function() {
                    $state.go('search-farespAppSuffix');
                });
            }]
        })
        .state('search-farespAppSuffix.edit', {
            parent: 'search-farespAppSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-fares/search-farespAppSuffix-dialog.html',
                    controller: 'SearchFaresPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SearchFares', function(SearchFares) {
                            return SearchFares.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('search-farespAppSuffix', null, { reload: 'search-farespAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('search-farespAppSuffix.delete', {
            parent: 'search-farespAppSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-fares/search-farespAppSuffix-delete-dialog.html',
                    controller: 'SearchFaresPAppSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SearchFares', function(SearchFares) {
                            return SearchFares.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('search-farespAppSuffix', null, { reload: 'search-farespAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
