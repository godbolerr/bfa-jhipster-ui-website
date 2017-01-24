(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('BookingRecordPAppSuffixDetailController', BookingRecordPAppSuffixDetailController);

    BookingRecordPAppSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BookingRecord', 'Passenger'];

    function BookingRecordPAppSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, BookingRecord, Passenger) {
        var vm = this;

        vm.bookingRecord = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bfalegacyApp:bookingRecordUpdate', function(event, result) {
            vm.bookingRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
