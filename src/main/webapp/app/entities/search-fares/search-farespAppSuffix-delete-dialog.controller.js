(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchFaresPAppSuffixDeleteController',SearchFaresPAppSuffixDeleteController);

    SearchFaresPAppSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'SearchFares'];

    function SearchFaresPAppSuffixDeleteController($uibModalInstance, entity, SearchFares) {
        var vm = this;

        vm.searchFares = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SearchFares.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
