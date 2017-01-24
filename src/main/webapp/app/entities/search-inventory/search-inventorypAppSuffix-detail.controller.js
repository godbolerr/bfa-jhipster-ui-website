(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchInventoryPAppSuffixDetailController', SearchInventoryPAppSuffixDetailController);

    SearchInventoryPAppSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SearchInventory'];

    function SearchInventoryPAppSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, SearchInventory) {
        var vm = this;

        vm.searchInventory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bfalegacyApp:searchInventoryUpdate', function(event, result) {
            vm.searchInventory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
