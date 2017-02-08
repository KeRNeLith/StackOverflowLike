describe("stackoverflowlikeagclient module", function() {
    var scope;

    beforeEach(angular.mock.module("stackoverflowlikeagclient", function() {
    }));

    beforeEach(angular.mock.inject(function($rootScope) {
        scope = $rootScope.$new();
    }));

    describe("QuestionsController", function() {

        var ctrl;

        beforeEach(angular.mock.inject(function($controller) {
            ctrl = $controller("QuestionsController", {});
        }));

        it("should be tested", function() {
            expect(true).toEqual(false);
        });

    });

});
