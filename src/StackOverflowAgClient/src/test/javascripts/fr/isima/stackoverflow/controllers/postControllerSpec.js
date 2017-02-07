describe("fr.isima.stackoverflow module", function() {
    var scope;

    beforeEach(angular.mock.module("fr.isima.stackoverflow", function() {
    }));

    beforeEach(angular.mock.inject(function($rootScope) {
        scope = $rootScope.$new();
    }));

    describe("PostController", function() {

        var ctrl;

        beforeEach(angular.mock.inject(function($controller) {
            ctrl = $controller("PostController", {});
        }));

        it("should be tested", function() {
            expect(true).toEqual(false);
        });

    });

});
