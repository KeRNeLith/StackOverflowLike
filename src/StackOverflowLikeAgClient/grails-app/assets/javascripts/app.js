angular.module('demo', [])
.controller('Hello', function($scope, $http) {
  // http://localhost:8080/api/question/home
  // http://rest-service.guides.spring.io/greeting
  // https://jsonplaceholder.typicode.com/posts/1

    return $http.get('http://localhost:8080').
        then(function(response) {
            $scope.greeting = response.data;
        });
});
