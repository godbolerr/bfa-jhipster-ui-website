(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('FaresPAppSuffixDialogController', FaresPAppSuffixDialogController);

    FaresPAppSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Fares'];

    function FaresPAppSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Fares) {
        var vm = this;

        vm.fares = entity;
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
            if (vm.fares.id !== null) {
                Fares.update(vm.fares, onSaveSuccess, onSaveError);
            } else {
                Fares.save(vm.fares, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bfalegacyApp:faresUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
