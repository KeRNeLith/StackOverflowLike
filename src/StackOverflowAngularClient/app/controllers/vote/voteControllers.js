/**
 * Created by kernelith on 25/02/17.
 */

'use strict';

var voteModule = angular.module('segFault.vote', []);

// Define controllers
voteModule.controller('VoteCtrl', function($scope, $http, API)
{
    var self = this;

    // Enum
    self.VoteEnum = {
        UP: "UP",
        DOWN: "DOWN"
    };

    self.lastVote = self.VoteEnum.NONE;

    self.vote = function(postId, value)
    {
        $http.post(API + '/api/vote/performVote', {
            post: postId,
            vote: value
        }).then(function successCallback(response)
            {
                // New vote created or Vote updated
                if (response.status == 201 || response.status == 200)
                {
                    // Increment : 1 for new votes and 2 when updating
                    let inc = 1;
                    if (response.status == 200)
                        inc = 2;

                    if (value == self.VoteEnum.UP)
                    {
                        $scope.nbVotes += inc;
                    }
                    else
                    {
                        $scope.nbVotes -= inc;
                    }
                }
                // Else Not modified vote (304)

                self.lastVote = value;
            },
            function errorCallback(response)
            {
                // Not modified
                if (response.status == 304)
                    console.log("Vote not modified");
                else
                    console.log("Fails to perform vote.");

                self.lastVote = value;
            });
    };
});