(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('PassengerPAppSuffixDetailController', PassengerPAppSuffixDetailController);

    PassengerPAppSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Passenger', 'BookingRecord'];

    function PassengerPAppSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Passenger, BookingRecord) {
        var vm = this;

        vm.passenger = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bfalegacyApp:passengerUpdate', function(event, result) {
            vm.passenger = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
