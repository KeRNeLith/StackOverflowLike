angular.module('demo', [])
.controller('Hello', function($scope, $http) {
    return $http.get('http://rest-service.guides.spring.io/greeting').
        success(function(response) {
            $scope.greeting = response.data;
        });
});
