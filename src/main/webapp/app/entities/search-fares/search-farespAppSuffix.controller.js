(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchFaresPAppSuffixController', SearchFaresPAppSuffixController);

    SearchFaresPAppSuffixController.$inject = ['$scope', '$state', 'SearchFares'];

    function SearchFaresPAppSuffixController ($scope, $state, SearchFares) {
        var vm = this;

        vm.searchFares = [];

        loadAll();

        function loadAll() {
            SearchFares.query(function(result) {
                vm.searchFares = result;
                vm.searchQuery = null;
            });
        }
    }
})();
