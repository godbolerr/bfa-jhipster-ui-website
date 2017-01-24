(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('inventorypAppSuffix', {
            parent: 'entity',
            url: '/inventorypAppSuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Inventories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/inventory/inventoriespAppSuffix.html',
                    controller: 'InventoryPAppSuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('inventorypAppSuffix-detail', {
            parent: 'entity',
            url: '/inventorypAppSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Inventory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/inventory/inventorypAppSuffix-detail.html',
                    controller: 'InventoryPAppSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Inventory', function($stateParams, Inventory) {
                    return Inventory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'inventorypAppSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('inventorypAppSuffix-detail.edit', {
            parent: 'inventorypAppSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inventory/inventorypAppSuffix-dialog.html',
                    controller: 'InventoryPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Inventory', function(Inventory) {
                            return Inventory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('inventorypAppSuffix.new', {
            parent: 'inventorypAppSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inventory/inventorypAppSuffix-dialog.html',
                    controller: 'InventoryPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                flightNumber: null,
                                flightDate: null,
                                available: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('inventorypAppSuffix', null, { reload: 'inventorypAppSuffix' });
                }, function() {
                    $state.go('inventorypAppSuffix');
                });
            }]
        })
        .state('inventorypAppSuffix.edit', {
            parent: 'inventorypAppSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inventory/inventorypAppSuffix-dialog.html',
                    controller: 'InventoryPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Inventory', function(Inventory) {
                            return Inventory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('inventorypAppSuffix', null, { reload: 'inventorypAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('inventorypAppSuffix.delete', {
            parent: 'inventorypAppSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/inventory/inventorypAppSuffix-delete-dialog.html',
                    controller: 'InventoryPAppSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Inventory', function(Inventory) {
                            return Inventory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('inventorypAppSuffix', null, { reload: 'inventorypAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
