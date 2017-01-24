(function() {
    'use strict';

    angular
        .module('bfalegacyApp')
        .controller('BookController', BookController);

    BookController.$inject = ['$rootScope','$scope', 'Principal',  '$state','$http','Base64'];

    function BookController ($rootScope, $scope, Principal,  $state,$http,Base64) {
        var vm = this;
        
        vm.bookFlight = bookFlight;
        vm.bookingResponse = null;
        

        vm.bookingInfo = {};
        vm.bookingInfo.flightNumber = $rootScope.flightNumber;
        vm.bookingInfo.origin = $rootScope.flightOrigin;
        vm.bookingInfo.destination = $rootScope.flightDestination;
        vm.bookingInfo.fare = $rootScope.flightFare;
        vm.bookingInfo.flightDate = $rootScope.flightDate;
        vm.bookingInfo.firstName = 'John';
        vm.bookingInfo.lastName = 'Right';
        vm.bookingInfo.gender = 'Male';
        
        
        
        function bookFlight(){
        	
        		vm.bookingResponse = null;
        	
    			var data = {

    				"flightNumber" :  vm.bookingInfo.flightNumber,
    				"origin" : vm.bookingInfo.origin,
    				"destination" : vm.bookingInfo.destination,
    				"flightDate" : vm.bookingInfo.flightDate ,
    				"bookingDate" : vm.bookingInfo.flightDate ,
    				"fare":vm.bookingInfo.fare,
    				"status":"Active",
    				"pdto":
    				    {"firstName":vm.bookingInfo.firstName, "lastName":vm.bookingInfo.lastName,"gender":vm.bookingInfo.gender}
    				
    			};

    			return $http
    					.post(
    							'/api/booking-records',
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
    				return response;
    			}

    		}
       	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
     
        
        
    }
})();
