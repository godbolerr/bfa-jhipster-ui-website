(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchFlightPAppSuffixDeleteController',SearchFlightPAppSuffixDeleteController);

    SearchFlightPAppSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'SearchFlight'];

    function SearchFlightPAppSuffixDeleteController($uibModalInstance, entity, SearchFlight) {
        var vm = this;

        vm.searchFlight = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SearchFlight.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
