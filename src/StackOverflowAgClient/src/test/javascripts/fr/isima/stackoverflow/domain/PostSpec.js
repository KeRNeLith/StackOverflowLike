describe("fr.isima.stackoverflow module", function() {
    var $httpBackend;

    beforeEach(angular.mock.module("fr.isima.stackoverflow", function() {
    }));

    beforeEach(angular.mock.inject(function(_$httpBackend_) {
        $httpBackend = _$httpBackend_;
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    describe("Post domain", function() {

        var Post;

        beforeEach(angular.mock.inject(function(_Post_) {
            Post = _Post_;
        }));

        it("should be tested", function() {
            expect(true).toEqual(false);
        });

    });

});
