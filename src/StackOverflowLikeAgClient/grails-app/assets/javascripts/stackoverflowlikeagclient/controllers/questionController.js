//= wrapped

angular
    .module("stackoverflowlikeagclient")
    .controller("QuestionController", QuestionController);

function QuestionController($scope, $http)
{
    var questionController = this;

    questionController.home = function ()
    {
        $http.get('localhost:8080/api/question/home')
             .then(function(response)
             {
                $scope.greeting = "some answer";
             });
    };
    
    /*
    var vm = this;
    return $http.get('localhost:8080/api/question/home').
    success(function(response){
      $scope.greeting = response;
    });
    //*/
}
