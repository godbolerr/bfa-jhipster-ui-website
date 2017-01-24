(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchInventoryPAppSuffixDeleteController',SearchInventoryPAppSuffixDeleteController);

    SearchInventoryPAppSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'SearchInventory'];

    function SearchInventoryPAppSuffixDeleteController($uibModalInstance, entity, SearchInventory) {
        var vm = this;

        vm.searchInventory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SearchInventory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
