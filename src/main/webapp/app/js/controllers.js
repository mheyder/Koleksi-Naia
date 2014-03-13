'use strict';

/* Controllers */

var koleksiNaiaControllers = angular.module('koleksiNaia.controllers', []);

koleksiNaiaControllers.controller('DashboardCtrl', [
  function() {
  }]);
  
// ***** ORDERS *****
koleksiNaiaControllers.controller('OrderCreateCtrl', ['$scope',
  function($scope) {
    $scope.newOrders = [{}];
    
    $scope.addRow = function() {
      $scope.newOrders.push({});
    };
    
    $scope.deleteRow = function(order) {
      $scope.newOrders.splice( $scope.newOrders.indexOf(order), 1 );
    };
  }]);

koleksiNaiaControllers.controller('OrderDetailCtrl', [
  function() {
  }]);


// ***** CUSTOMER *****
// *CUST: customer LIST page controller
koleksiNaiaControllers.controller('CustListCtrl', ['$scope', 'Customer',
  function($scope, Customer) {
    $scope.customers = Customer.query();
  }]);

// *CUST: customer CREATE controller
//        handles the creation of new customer
koleksiNaiaControllers.controller('CustCreateCtrl', ['$scope', '$window', 'Customer',
  function($scope, $window, Customer) {
	$scope.pageHeader = "New Customer";
    $scope.isIdDisable = false;
    $scope.customer = {};
    $scope.alert = {"exist":"false"};
                                                                                                        
    $scope.saveCustomer = function() {
      Customer.create($scope.customer, 
        function(){
    	  $scope.alert = {"type":"alert alert-success alert-dismissable", 
    			          "msg":"Customer successfully created.", 
    			          "exist":"true"};
    	  $scope.customer = {};
        },
        function(err){
        	$scope.alert = {"type":"alert alert-danger alert-dismissable", 
                            "msg":"Failed. Error: "+ err.status, 
                            "exist":"true"};
        });
    };   
  }]);

koleksiNaiaControllers.controller('CustOrderCtrl', [
  function() {
  }]);
  
// *CUST: customer EDIT controller
koleksiNaiaControllers.controller('CustEditCtrl', ['$scope', '$route', '$window', 'Customer',
  function($scope, $route, $window, Customer) {
	Customer.get({customerId:$route.current.params.customerId},
      function(customerData) {
        $scope.customer = customerData;
      },
      function(err){
        $window.location = '/app/index.html';
      });
    $scope.pageHeader = "Edit Customer";
    $scope.isIdDisable = true;
    $scope.alert = {"exist":"false"};
		    
    $scope.saveCustomer = function() {
      Customer.update({customerId:$scope.customer.id}, $scope.customer, 
        function(){
          $scope.alert = {"type":"alert alert-success alert-dismissable", 
        		          "msg":"Save customer succeed.", 
        		          "exist":"true"};
          },
          function(err){
            $scope.alert = {"type":"alert alert-danger alert-dismissable", 
            		        "msg":"Failed. Error: "+ err.status, 
            		        "exist":"true"};
		  });
        };
  }]);

// ***** SUPPLIER *****
// *SUPP: supplier list page controller
koleksiNaiaControllers.controller('SuppListCtrl', ['$scope', 'Supplier',
  function($scope, Supplier) {
    $scope.suppliers = Supplier.query();
  }]);

// *SUPP: supplier create controller
//        handles the creation of new supplier
koleksiNaiaControllers.controller('SuppCreateCtrl', ['$scope', '$window', 'Supplier',
  function($scope, $window, Supplier) {
	$scope.pageHeader = "New Supplier";
	$scope.isIdDisable = false;
    $scope.supplier = {};
    $scope.alert = {"exist":"false"};
                                                                                                        
    $scope.saveSupplier = function() {
      Supplier.create($scope.supplier, 
        function(){
    	  $scope.alert = {"type":"alert alert-success alert-dismissable", 
    			          "msg":"Supplier successfully created.", 
    			          "exist":"true"};
    	  $scope.supplier = {};
        },
        function(err){
        	$scope.alert = {"type":"alert alert-danger alert-dismissable", 
                            "msg":"Failed. Error: "+err.status, 
                            "exist":"true"};
        });
    };                                          
  }]);

// *SUPP: supplier EDIT controller
koleksiNaiaControllers.controller('SuppEditCtrl', ['$scope', '$route', '$window', 'Supplier',
  function($scope, $route, $window, Supplier) {
	Supplier.get({supplierId:$route.current.params.supplierId},
	  function(supplierData) {
		$scope.supplier = supplierData;
	  },
	  function(err){
		  $window.location = '/app/index.html';
	});
	$scope.pageHeader = "Edit Supplier";
	$scope.isIdDisable = true;
    $scope.alert = {"exist":"false"};
    
    $scope.saveSupplier = function() {
    	Supplier.update({supplierId:$scope.supplier.id}, $scope.supplier, 
        function(){
        	$scope.alert = {"type":"alert alert-success alert-dismissable", "msg":"Save supplier succedd.", "exist":"true"};
        },
        function(err){
        	$scope.alert = {"type":"alert alert-danger alert-dismissable", "msg":"Save supplier failed.", "exist":"true"};
        });
    };
  }]);

koleksiNaiaControllers.controller('SuppOrderCtrl', [
  function() {
  }]);

//koleksiNaiaControllers.controller('SuppEditCtrl', ['$scope', '$routeParams', 'Supplier',
//                                                   function($scope, $routeParams, Supplier) {
//                                                 	$scope.pageHeader = "Edit Supplier";
//                                                     $scope.supplier = Supplier.get({supplierId:$routeParams.supplierId});
//                                                     
//                                                     $scope.saveSupplier() = function() {
//                                                       Supplier.create($scope.supplier, 
//                                                         function(supp){
//                                                       	  $window.location = '/app/index.html#/suppliers/'+supp.id;
//                                                         },
//                                                         function(err){
//                                                             alert('request failed');
//                                                         });
//                                                     };
//                                                   }]);

//koleksiNaiaControllers.controller('SuppEditCtrl.loadData', ['$routeParams', 'Supplier',
//  supplierData: function($routeParams, Supplier) {
//    return Supplier.get({supplierId:$routeParams.supplierId});
//  }]);
