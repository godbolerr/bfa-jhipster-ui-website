(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('PassengerPAppSuffixDialogController', PassengerPAppSuffixDialogController);

    PassengerPAppSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Passenger', 'BookingRecord'];

    function PassengerPAppSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Passenger, BookingRecord) {
        var vm = this;

        vm.passenger = entity;
        vm.clear = clear;
        vm.save = save;
        vm.bookingrecords = BookingRecord.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.passenger.id !== null) {
                Passenger.update(vm.passenger, onSaveSuccess, onSaveError);
            } else {
                Passenger.save(vm.passenger, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bfalegacyApp:passengerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
