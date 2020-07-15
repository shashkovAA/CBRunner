var app = angular.module("CBRunnerApp", []);

app.controller("TestController", function($scope, $http) {


    $scope.test = function() {
    $scope.result = "Запрос выполнен";
    };


    $scope.testGetOneTicket = function() {

        var method = "GET";
        var url = "/api/testGetOneTicket/" + $scope.ticketId;

        $http({
            method: method,
            url: url,
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(
            function(res) { // success
                //$scope.result = JSON.stringify(res.data);
                var jsonResp = angular.toJson(res.data);
                $scope.result = jsonResp
                console.log("Response: " + jsonResp);
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
                _error(res);
            });

    };


    function _success(res) {
        $scope.result = "Success";
    }

    function _error(res) {
        $scope.result = "Failed.";
        var data = res.data;
        var status = res.status;
        var header = res.header;
        var config = res.config;
        $scope.result = "Failed." + "Error: " + status;
        //alert("Error: " + status + ":" + data);
    }
/*
    // Clear the form
    function _clearFormData() {
        $scope.propertyForm.id = 0;
        $scope.propertyForm.name = "";
        $scope.propertyForm.value = ""
        $scope.propertyForm.description = ""
    };*/
});