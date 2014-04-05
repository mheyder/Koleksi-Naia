'use strict';

/* Controllers */

var CustomerControllers = angular.module('customerController', []);

//***** CUSTOMER *****
//*CUST: customer LIST page controller
CustomerControllers.controller('CustListCtrl', ['$scope', 'Customer',
  function($scope, Customer) {
    $scope.customers = Customer.query();
  }]);

//*CUST: customer CREATE controller
//     handles the creation of new customer
CustomerControllers.controller('CustCreateCtrl', ['$scope', '$window', 'Customer',
  function($scope, $window, Customer) {
    $scope.pageHeader = "New Customer";
    $scope.isIdDisable = false;
    $scope.customer = {};
   
    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };
                                                                                                       
    $scope.saveCustomer = function() {
      Customer.create($scope.customer, 
        function(){
          $scope.alerts = [{ type: 'success', msg: 'Customer successfully created.' }];
          $scope.customer = {};
        },
        function(err){
          $scope.alerts = [{ type: 'danger', msg: 'Failed. Error:' + err.status}];
        });
   };   
  }]);

//*CUST: customer EDIT controller
CustomerControllers.controller('CustEditCtrl', ['$scope', '$routeParams', '$window', 'Customer',
  function($scope, $routeParams, $window, Customer) {
    Customer.get({customerId:$routeParams.customerId},
      function(customerData) {
        $scope.customer = customerData;
      },
      function(err){
        $window.location = '/app/index.html';
      });
    $scope.pageHeader = "Edit Customer";
    $scope.isIdDisable = true;
   
    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };
          
    $scope.saveCustomer = function() {
      Customer.update({customerId:$scope.customer.id}, $scope.customer, 
        function(){
          $scope.alerts = [{ type: 'success', msg: 'Customer successfully updated.' }];
        },
        function(err){
          $scope.alerts = [{ type: 'danger', msg: 'Failed. Error:' + err.status}];
        });
    };
  }]);
