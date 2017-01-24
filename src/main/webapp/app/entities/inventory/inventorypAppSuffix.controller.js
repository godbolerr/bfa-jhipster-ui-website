(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('InventoryPAppSuffixController', InventoryPAppSuffixController);

    InventoryPAppSuffixController.$inject = ['$scope', '$state', 'Inventory'];

    function InventoryPAppSuffixController ($scope, $state, Inventory) {
        var vm = this;

        vm.inventories = [];

        loadAll();

        function loadAll() {
            Inventory.query(function(result) {
                vm.inventories = result;
                vm.searchQuery = null;
            });
        }
    }
})();
