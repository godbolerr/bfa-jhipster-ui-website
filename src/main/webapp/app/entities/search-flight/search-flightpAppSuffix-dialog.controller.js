(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('SearchFlightPAppSuffixDialogController', SearchFlightPAppSuffixDialogController);

    SearchFlightPAppSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SearchFlight', 'SearchFares', 'SearchInventory'];

    function SearchFlightPAppSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, SearchFlight, SearchFares, SearchInventory) {
        var vm = this;

        vm.searchFlight = entity;
        vm.clear = clear;
        vm.save = save;
        vm.sflightfares = SearchFares.query({filter: 'searchflight-is-null'});
        $q.all([vm.searchFlight.$promise, vm.sflightfares.$promise]).then(function() {
            if (!vm.searchFlight.sFlightFareId) {
                return $q.reject();
            }
            return SearchFares.get({id : vm.searchFlight.sFlightFareId}).$promise;
        }).then(function(sFlightFare) {
            vm.sflightfares.push(sFlightFare);
        });
        vm.sflightinvs = SearchInventory.query({filter: 'searchflight-is-null'});
        $q.all([vm.searchFlight.$promise, vm.sflightinvs.$promise]).then(function() {
            if (!vm.searchFlight.sFlightInvId) {
                return $q.reject();
            }
            return SearchInventory.get({id : vm.searchFlight.sFlightInvId}).$promise;
        }).then(function(sFlightInv) {
            vm.sflightinvs.push(sFlightInv);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.searchFlight.id !== null) {
                SearchFlight.update(vm.searchFlight, onSaveSuccess, onSaveError);
            } else {
                SearchFlight.save(vm.searchFlight, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bfalegacyApp:searchFlightUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
