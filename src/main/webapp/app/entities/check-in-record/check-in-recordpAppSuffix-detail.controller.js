(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('CheckInRecordPAppSuffixDetailController', CheckInRecordPAppSuffixDetailController);

    CheckInRecordPAppSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CheckInRecord'];

    function CheckInRecordPAppSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, CheckInRecord) {
        var vm = this;

        vm.checkInRecord = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bfalegacyApp:checkInRecordUpdate', function(event, result) {
            vm.checkInRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
