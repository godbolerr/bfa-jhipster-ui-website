(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchInventoryPAppSuffixDialogController', SearchInventoryPAppSuffixDialogController);

    SearchInventoryPAppSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SearchInventory'];

    function SearchInventoryPAppSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SearchInventory) {
        var vm = this;

        vm.searchInventory = entity;
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
            if (vm.searchInventory.id !== null) {
                SearchInventory.update(vm.searchInventory, onSaveSuccess, onSaveError);
            } else {
                SearchInventory.save(vm.searchInventory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bfalegacyApp:searchInventoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
