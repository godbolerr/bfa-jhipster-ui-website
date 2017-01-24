(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('FaresPAppSuffixDeleteController',FaresPAppSuffixDeleteController);

    FaresPAppSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Fares'];

    function FaresPAppSuffixDeleteController($uibModalInstance, entity, Fares) {
        var vm = this;

        vm.fares = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Fares.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
