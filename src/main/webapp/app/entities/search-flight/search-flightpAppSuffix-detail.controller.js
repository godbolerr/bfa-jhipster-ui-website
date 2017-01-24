(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchFlightPAppSuffixDetailController', SearchFlightPAppSuffixDetailController);

    SearchFlightPAppSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SearchFlight', 'SearchFares', 'SearchInventory'];

    function SearchFlightPAppSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, SearchFlight, SearchFares, SearchInventory) {
        var vm = this;

        vm.searchFlight = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bfalegacyApp:searchFlightUpdate', function(event, result) {
            vm.searchFlight = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
