(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('search-inventorypAppSuffix', {
            parent: 'entity',
            url: '/search-inventorypAppSuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SearchInventories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/search-inventory/search-inventoriespAppSuffix.html',
                    controller: 'SearchInventoryPAppSuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('search-inventorypAppSuffix-detail', {
            parent: 'entity',
            url: '/search-inventorypAppSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SearchInventory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/search-inventory/search-inventorypAppSuffix-detail.html',
                    controller: 'SearchInventoryPAppSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SearchInventory', function($stateParams, SearchInventory) {
                    return SearchInventory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'search-inventorypAppSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('search-inventorypAppSuffix-detail.edit', {
            parent: 'search-inventorypAppSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-inventory/search-inventorypAppSuffix-dialog.html',
                    controller: 'SearchInventoryPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SearchInventory', function(SearchInventory) {
                            return SearchInventory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('search-inventorypAppSuffix.new', {
            parent: 'search-inventorypAppSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-inventory/search-inventorypAppSuffix-dialog.html',
                    controller: 'SearchInventoryPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                count: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('search-inventorypAppSuffix', null, { reload: 'search-inventorypAppSuffix' });
                }, function() {
                    $state.go('search-inventorypAppSuffix');
                });
            }]
        })
        .state('search-inventorypAppSuffix.edit', {
            parent: 'search-inventorypAppSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-inventory/search-inventorypAppSuffix-dialog.html',
                    controller: 'SearchInventoryPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SearchInventory', function(SearchInventory) {
                            return SearchInventory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('search-inventorypAppSuffix', null, { reload: 'search-inventorypAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('search-inventorypAppSuffix.delete', {
            parent: 'search-inventorypAppSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/search-inventory/search-inventorypAppSuffix-delete-dialog.html',
                    controller: 'SearchInventoryPAppSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SearchInventory', function(SearchInventory) {
                            return SearchInventory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('search-inventorypAppSuffix', null, { reload: 'search-inventorypAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
