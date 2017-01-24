(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('PassengerPAppSuffixDeleteController',PassengerPAppSuffixDeleteController);

    PassengerPAppSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Passenger'];

    function PassengerPAppSuffixDeleteController($uibModalInstance, entity, Passenger) {
        var vm = this;

        vm.passenger = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Passenger.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
