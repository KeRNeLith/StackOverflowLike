//= wrapped

angular
    .module("stackoverflowlikeagclient")
    .controller("QuestionsController", QuestionsController);

function QuestionsController($scope, $http) {
    /*
    var vm = this;
    return $http.get('localhost:8080/api/question/home').
    success(function(response){
      $scope.greeting = response;
    });
    //*/
    $http.get('localhost:8080/api/question/home').
    then(function(response){
      $scope.greeting = "some answer";
    });
}
