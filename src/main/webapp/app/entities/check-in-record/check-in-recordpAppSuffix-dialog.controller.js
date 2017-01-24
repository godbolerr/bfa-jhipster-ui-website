(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('CheckInRecordPAppSuffixDialogController', CheckInRecordPAppSuffixDialogController);

    CheckInRecordPAppSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CheckInRecord'];

    function CheckInRecordPAppSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CheckInRecord) {
        var vm = this;

        vm.checkInRecord = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.checkInRecord.id !== null) {
                CheckInRecord.update(vm.checkInRecord, onSaveSuccess, onSaveError);
            } else {
                CheckInRecord.save(vm.checkInRecord, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bfalegacyApp:checkInRecordUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.checkInTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
