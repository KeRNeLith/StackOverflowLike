//= wrapped

angular
    .module("stackoverflowlikeagclient")
    .controller("QuestionsController", QuestionsController);

function QuestionsController($scope, $http) {
    var vm = this;
    $http.get('localhost:8080/api/questions/home').
    then(function(response){
      $scope.greeting = response.data;
    });
}
