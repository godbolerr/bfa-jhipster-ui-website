(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchInventoryPAppSuffixController', SearchInventoryPAppSuffixController);

    SearchInventoryPAppSuffixController.$inject = ['$scope', '$state', 'SearchInventory'];

    function SearchInventoryPAppSuffixController ($scope, $state, SearchInventory) {
        var vm = this;

        vm.searchInventories = [];

        loadAll();

        function loadAll() {
            SearchInventory.query(function(result) {
                vm.searchInventories = result;
                vm.searchQuery = null;
            });
        }
    }
})();
