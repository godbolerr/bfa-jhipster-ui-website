(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('InventoryPAppSuffixDeleteController',InventoryPAppSuffixDeleteController);

    InventoryPAppSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Inventory'];

    function InventoryPAppSuffixDeleteController($uibModalInstance, entity, Inventory) {
        var vm = this;

        vm.inventory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Inventory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
