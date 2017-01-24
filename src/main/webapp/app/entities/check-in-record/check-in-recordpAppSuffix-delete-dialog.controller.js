(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('CheckInRecordPAppSuffixDeleteController',CheckInRecordPAppSuffixDeleteController);

    CheckInRecordPAppSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'CheckInRecord'];

    function CheckInRecordPAppSuffixDeleteController($uibModalInstance, entity, CheckInRecord) {
        var vm = this;

        vm.checkInRecord = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CheckInRecord.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
