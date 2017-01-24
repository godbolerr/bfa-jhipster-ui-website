(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('farespAppSuffix', {
            parent: 'entity',
            url: '/farespAppSuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fares'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fares/farespAppSuffix.html',
                    controller: 'FaresPAppSuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('farespAppSuffix-detail', {
            parent: 'entity',
            url: '/farespAppSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fares'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fares/farespAppSuffix-detail.html',
                    controller: 'FaresPAppSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Fares', function($stateParams, Fares) {
                    return Fares.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'farespAppSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('farespAppSuffix-detail.edit', {
            parent: 'farespAppSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fares/farespAppSuffix-dialog.html',
                    controller: 'FaresPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fares', function(Fares) {
                            return Fares.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('farespAppSuffix.new', {
            parent: 'farespAppSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fares/farespAppSuffix-dialog.html',
                    controller: 'FaresPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                flightNumber: null,
                                flightDate: null,
                                fare: null,
                                currency: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('farespAppSuffix', null, { reload: 'farespAppSuffix' });
                }, function() {
                    $state.go('farespAppSuffix');
                });
            }]
        })
        .state('farespAppSuffix.edit', {
            parent: 'farespAppSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fares/farespAppSuffix-dialog.html',
                    controller: 'FaresPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fares', function(Fares) {
                            return Fares.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('farespAppSuffix', null, { reload: 'farespAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('farespAppSuffix.delete', {
            parent: 'farespAppSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fares/farespAppSuffix-delete-dialog.html',
                    controller: 'FaresPAppSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Fares', function(Fares) {
                            return Fares.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('farespAppSuffix', null, { reload: 'farespAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
