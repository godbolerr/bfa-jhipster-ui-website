(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('BookingRecordPAppSuffixDialogController', BookingRecordPAppSuffixDialogController);

    BookingRecordPAppSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BookingRecord', 'Passenger'];

    function BookingRecordPAppSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BookingRecord, Passenger) {
        var vm = this;

        vm.bookingRecord = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.passengers = Passenger.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookingRecord.id !== null) {
                BookingRecord.update(vm.bookingRecord, onSaveSuccess, onSaveError);
            } else {
                BookingRecord.save(vm.bookingRecord, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bfalegacyApp:bookingRecordUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.bookingDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
