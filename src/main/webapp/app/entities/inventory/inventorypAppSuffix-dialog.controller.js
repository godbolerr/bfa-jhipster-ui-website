(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('InventoryPAppSuffixDialogController', InventoryPAppSuffixDialogController);

    InventoryPAppSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Inventory'];

    function InventoryPAppSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Inventory) {
        var vm = this;

        vm.inventory = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.inventory.id !== null) {
                Inventory.update(vm.inventory, onSaveSuccess, onSaveError);
            } else {
                Inventory.save(vm.inventory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bfalegacyApp:inventoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
