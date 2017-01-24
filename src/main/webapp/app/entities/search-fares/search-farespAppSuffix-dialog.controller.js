(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchFaresPAppSuffixDialogController', SearchFaresPAppSuffixDialogController);

    SearchFaresPAppSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SearchFares'];

    function SearchFaresPAppSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SearchFares) {
        var vm = this;

        vm.searchFares = entity;
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
            if (vm.searchFares.id !== null) {
                SearchFares.update(vm.searchFares, onSaveSuccess, onSaveError);
            } else {
                SearchFares.save(vm.searchFares, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bfalegacyApp:searchFaresUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
