(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('booking-recordpAppSuffix', {
            parent: 'entity',
            url: '/booking-recordpAppSuffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BookingRecords'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/booking-record/booking-recordspAppSuffix.html',
                    controller: 'BookingRecordPAppSuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('booking-recordpAppSuffix-detail', {
            parent: 'entity',
            url: '/booking-recordpAppSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BookingRecord'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/booking-record/booking-recordpAppSuffix-detail.html',
                    controller: 'BookingRecordPAppSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BookingRecord', function($stateParams, BookingRecord) {
                    return BookingRecord.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'booking-recordpAppSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('booking-recordpAppSuffix-detail.edit', {
            parent: 'booking-recordpAppSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/booking-record/booking-recordpAppSuffix-dialog.html',
                    controller: 'BookingRecordPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookingRecord', function(BookingRecord) {
                            return BookingRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('booking-recordpAppSuffix.new', {
            parent: 'booking-recordpAppSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/booking-record/booking-recordpAppSuffix-dialog.html',
                    controller: 'BookingRecordPAppSuffixDialogController',
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
                                bookingDate: null,
                                fare: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('booking-recordpAppSuffix', null, { reload: 'booking-recordpAppSuffix' });
                }, function() {
                    $state.go('booking-recordpAppSuffix');
                });
            }]
        })
        .state('booking-recordpAppSuffix.edit', {
            parent: 'booking-recordpAppSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/booking-record/booking-recordpAppSuffix-dialog.html',
                    controller: 'BookingRecordPAppSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookingRecord', function(BookingRecord) {
                            return BookingRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('booking-recordpAppSuffix', null, { reload: 'booking-recordpAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('booking-recordpAppSuffix.delete', {
            parent: 'booking-recordpAppSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/booking-record/booking-recordpAppSuffix-delete-dialog.html',
                    controller: 'BookingRecordPAppSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BookingRecord', function(BookingRecord) {
                            return BookingRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('booking-recordpAppSuffix', null, { reload: 'booking-recordpAppSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
