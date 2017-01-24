'use strict';

describe('Controller Tests', function() {

    describe('SearchFlight Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSearchFlight, MockSearchFares, MockSearchInventory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSearchFlight = jasmine.createSpy('MockSearchFlight');
            MockSearchFares = jasmine.createSpy('MockSearchFares');
            MockSearchInventory = jasmine.createSpy('MockSearchInventory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SearchFlight': MockSearchFlight,
                'SearchFares': MockSearchFares,
                'SearchInventory': MockSearchInventory
            };
            createController = function() {
                $injector.get('$controller')("SearchFlightPAppSuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bfalegacyApp:searchFlightUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
