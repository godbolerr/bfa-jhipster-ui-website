(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('CheckInRecordPAppSuffixController', CheckInRecordPAppSuffixController);

    CheckInRecordPAppSuffixController.$inject = ['$scope', '$state', 'CheckInRecord'];

    function CheckInRecordPAppSuffixController ($scope, $state, CheckInRecord) {
        var vm = this;

        vm.checkInRecords = [];

        loadAll();

        function loadAll() {
            CheckInRecord.query(function(result) {
                vm.checkInRecords = result;
                vm.searchQuery = null;
            });
        }
    }
})();
