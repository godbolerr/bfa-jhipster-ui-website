(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('InventoryPAppSuffixDetailController', InventoryPAppSuffixDetailController);

    InventoryPAppSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Inventory'];

    function InventoryPAppSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Inventory) {
        var vm = this;

        vm.inventory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bfalegacyApp:inventoryUpdate', function(event, result) {
            vm.inventory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
