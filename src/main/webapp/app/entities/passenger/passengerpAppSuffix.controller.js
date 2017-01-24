(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('PassengerPAppSuffixController', PassengerPAppSuffixController);

    PassengerPAppSuffixController.$inject = ['$scope', '$state', 'Passenger'];

    function PassengerPAppSuffixController ($scope, $state, Passenger) {
        var vm = this;

        vm.passengers = [];

        loadAll();

        function loadAll() {
            Passenger.query(function(result) {
                vm.passengers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
