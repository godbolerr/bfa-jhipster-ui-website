(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchFlightPAppSuffixController', SearchFlightPAppSuffixController);

    SearchFlightPAppSuffixController.$inject = ['$scope', '$state', 'SearchFlight'];

    function SearchFlightPAppSuffixController ($scope, $state, SearchFlight) {
        var vm = this;

        vm.searchFlights = [];

        loadAll();

        function loadAll() {
            SearchFlight.query(function(result) {
                vm.searchFlights = result;
                vm.searchQuery = null;
            });
        }
    }
})();
