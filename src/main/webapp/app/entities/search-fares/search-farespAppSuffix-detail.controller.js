(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchFaresPAppSuffixDetailController', SearchFaresPAppSuffixDetailController);

    SearchFaresPAppSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SearchFares'];

    function SearchFaresPAppSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, SearchFares) {
        var vm = this;

        vm.searchFares = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bfalegacyApp:searchFaresUpdate', function(event, result) {
            vm.searchFares = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
