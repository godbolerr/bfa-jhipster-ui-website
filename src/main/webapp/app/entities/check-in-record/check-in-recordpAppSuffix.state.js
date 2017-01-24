(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('check-in-recordpAppSuffix', {
            parent: 'entity',
            url: '/check-in-recordpAppSuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CheckInRecords'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/check-in-record/check-in-recordspAppSuffix.html',
                    controller: 'CheckInRecordPAppSuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('check-in-recordpAppSuffix-detail', {
            parent: 'entity',
            url: '/check-in-recordpAppSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CheckInRecord'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/check-in-record/check-in-recordpAppSuffix-detail.html',
                    controller: 'CheckInRecordPAppSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CheckInRecord', function($stateParams, CheckInRecord) {
                    return CheckInRecord.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'check-in-recordpAppSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('check-in-recordpAppSuffix-detail.edit', {
            parent: 'check-in-recordpAppSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/check-in-record/check-in-recordpAppSuffix-dialog.html',
                    controller: 'CheckInRecordPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CheckInRecord', function(CheckInRecord) {
                            return CheckInRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('check-in-recordpAppSuffix.new', {
            parent: 'check-in-recordpAppSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/check-in-record/check-in-recordpAppSuffix-dialog.html',
                    controller: 'CheckInRecordPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lastName: null,
                                firstName: null,
                                seatNumber: null,
                                checkInTime: null,
                                flightNumber: null,
                                flightDate: null,
                                bookingId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('check-in-recordpAppSuffix', null, { reload: 'check-in-recordpAppSuffix' });
                }, function() {
                    $state.go('check-in-recordpAppSuffix');
                });
            }]
        })
        .state('check-in-recordpAppSuffix.edit', {
            parent: 'check-in-recordpAppSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/check-in-record/check-in-recordpAppSuffix-dialog.html',
                    controller: 'CheckInRecordPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CheckInRecord', function(CheckInRecord) {
                            return CheckInRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('check-in-recordpAppSuffix', null, { reload: 'check-in-recordpAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('check-in-recordpAppSuffix.delete', {
            parent: 'check-in-recordpAppSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/check-in-record/check-in-recordpAppSuffix-delete-dialog.html',
                    controller: 'CheckInRecordPAppSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CheckInRecord', function(CheckInRecord) {
                            return CheckInRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('check-in-recordpAppSuffix', null, { reload: 'check-in-recordpAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
