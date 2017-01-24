(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('FaresPAppSuffixController', FaresPAppSuffixController);

    FaresPAppSuffixController.$inject = ['$scope', '$state', 'Fares'];

    function FaresPAppSuffixController ($scope, $state, Fares) {
        var vm = this;

        vm.fares = [];

        loadAll();

        function loadAll() {
            Fares.query(function(result) {
                vm.fares = result;
                vm.searchQuery = null;
            });
        }
    }
})();
