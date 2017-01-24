(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('FaresPAppSuffixDetailController', FaresPAppSuffixDetailController);

    FaresPAppSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Fares'];

    function FaresPAppSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Fares) {
        var vm = this;

        vm.fares = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bfalegacyApp:faresUpdate', function(event, result) {
            vm.fares = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
