(function() {
	'use strict';

	angular.module('bfalegacyApp').controller('HomeController', HomeController);

	HomeController.$inject = [ '$rootScope' ,'$http','$scope', 'Principal', '$state','Base64' ];

	function HomeController($rootScope,$http,$scope, Principal, $state,Base64) {
		var vm = this;
		vm.searchF = {};
		vm.searchF.origin = 'NYC';
		vm.searchF.destination = 'SFO';
		vm.searchF.flightDate = '2017-01-01';
		vm.flights = {};

		vm.searchFlight = searchFlight;
		vm.bookFlight = bookFlight;
		
		
		
		function bookFlight(origin,destination,flightNumber,flightDate,fare){
			
			//Temporarily binding to rootScope
			//Should be moved to service 
			$rootScope.flightOrigin = origin;
			$rootScope.flightDestination = destination;
			$rootScope.flightNumber = flightNumber;
			$rootScope.flightDate = flightDate;
			$rootScope.flightFare = fare;
			
			$state.go('book');
			
			
		}

		function searchFlight() {

			var data = {

				"origin" : vm.searchF.origin,
				"destination" : vm.searchF.destination,
				"flightDate" : vm.searchF.flightDate

			};

			return $http
					.post(
							'/api/searchFlights',
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
				vm.flights = response;
				vm.hasFlights = true;
				return response;
			}

		}

	}
})();
