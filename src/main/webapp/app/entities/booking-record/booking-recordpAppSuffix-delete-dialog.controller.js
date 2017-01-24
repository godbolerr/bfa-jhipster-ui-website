(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('BookingRecordPAppSuffixDeleteController',BookingRecordPAppSuffixDeleteController);

    BookingRecordPAppSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'BookingRecord'];

    function BookingRecordPAppSuffixDeleteController($uibModalInstance, entity, BookingRecord) {
        var vm = this;

        vm.bookingRecord = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BookingRecord.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
