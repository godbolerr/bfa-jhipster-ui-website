(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('CheckinController', CheckinController);

    CheckinController.$inject = ['$rootScope','$http','Base64','$scope', 'Principal',  '$state'];

    function CheckinController ($rootScope,$http,Base64, $scope, Principal, $state) {
        var vm = this;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.checkinResponse = null;
        vm.bookingInfo = {};
        
        
        vm.searchBooking = searchBooking;
        
        vm.checkIn = checkIn;
        
        function searchBooking(){
        	
    		vm.bookingResponse = null;
    	
			var data = {

				"bookingId" :  vm.bookingInfo.bookingId
			};

			return $http
					.get(
							'/api/booking-records/'+ vm.bookingInfo.bookingId,
							data,
							{
								headers : {
									'Content-Type' : 'application/json',
									'Accept' : 'application/json',
									'Authorization' : 'Basic '
											+ Base64
													.encode('legacypssapp'
															+ ':'
															+ 'my-secret-token-to-change-in-production')
								}
							}).success(authSucess);

			function authSucess(response) {
				var expiredAt = new Date();
				expiredAt.setSeconds(expiredAt.getSeconds()
						+ response.expires_in);
				response.expires_at = expiredAt.getTime();
				vm.bookingResponse = response;
				$rootScope.firstName = response.pdto.firstName;
				$rootScope.lastName = response.pdto.lastName;
				return response;
			}

		}
   	
    	        
        function checkIn(){
        	
    		
    	
			var data = {

				"bookingId" :  vm.bookingInfo.bookingId,
				"lastName":$rootScope.firstName,
				"firstName":$rootScope.lastName,
				"seatNumber":"28C",
				"checkInTime":"2017-01-01",
				"flightNumber":vm.bookingResponse.flightNumber,
				"flightDate":vm.bookingResponse.flightDate
			};

			return $http
					.post(
							'/api/check-in-records',
							data,
							{
								headers : {
									'Content-Type' : 'application/json',
									'Accept' : 'application/json',
									'Authorization' : 'Basic '
											+ Base64
													.encode('legacypssapp'
															+ ':'
															+ 'my-secret-token-to-change-in-production')
								}
							}).success(authSucess);

			function authSucess(response) {
				var expiredAt = new Date();
				expiredAt.setSeconds(expiredAt.getSeconds()
						+ response.expires_in);
				response.expires_at = expiredAt.getTime();
				vm.checkinResponse = response;
				vm.bookingResponse = null;
				return response;
			}

		}
   	
        
        
    }
})();
