'use strict';

/* Controllers */

var koleksiNaiaControllers = angular.module('koleksiNaia.controllers', []);

koleksiNaiaControllers.controller('DashboardCtrl', [
  function() {
  }]);
  
// ***** ORDERS *****
koleksiNaiaControllers.controller('OrderListCtrl', ['$scope', 'Order',
  function($scope, Order) {
    $scope.orders = Order.query();
  }]);

koleksiNaiaControllers.controller('OrderCreateCtrl', ['$scope', '$http', 'Order', 'Supplier', 'Customer',
  function($scope, $http, Order, Supplier, Customer) {
    $scope.suppliers = Supplier.query();
    $scope.customers = Customer.query();
    $scope.newOrders = [{date: new Date()}];
    $scope.maxDate = new Date();
    
    //****************
    //$scope.selected = undefined;
    $scope.getLocation = function(val) {
        return $http.get('http://maps.googleapis.com/maps/api/geocode/json', {
          params: {
            address: val,
            sensor: false
          }
        }).then(function(res){
          var addresses = [];
          angular.forEach(res.data.results, function(item){
            addresses.push(item.formatted_address);
          });
          return addresses;
        });
      };
    //****************
    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };
    $scope.addRow = function() {
      $scope.newOrders.push({date: new Date()});
    };
    
    $scope.deleteRow = function(order) {
      $scope.newOrders.splice( $scope.newOrders.indexOf(order), 1 );
    };

    $scope.saveOrders = function() {
      $scope.submitted = true;
      if ($scope.createOrdersForm.$valid) {	
        Order.create($scope.newOrders, 
          function(){
  	        $scope.alerts = [{ type: 'success', msg: 'Orders successfully created.' }];
  	        $scope.newOrders = [{date: new Date()}];
          },
          function(err){
      	    $scope.alerts = [{ type: 'danger', msg: 'Failed. Error:' + err.status}];
        });
      } else {
    	  $scope.alerts = [{ type: 'danger', msg: 'Please filled the required inputs'}];
      };
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

koleksiNaiaControllers.controller('CustOrderCtrl', [
  function() {
  }]);
  
// *CUST: customer EDIT controller
koleksiNaiaControllers.controller('CustEditCtrl', ['$scope', '$routeParams', '$window', 'Customer',
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
    
    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };
                                                                                                        
    $scope.saveSupplier = function() {
      Supplier.create($scope.supplier, 
        function(){
    	  $scope.alerts = [{ type: 'success', msg: 'Supplier successfully created.' }];
    	  $scope.supplier = {};
        },
        function(err){
        	$scope.alerts = [{ type: 'danger', msg: 'Failed. Error:' + err.status}];
        });
    };                                          
  }]);

// *SUPP: supplier EDIT controller
koleksiNaiaControllers.controller('SuppEditCtrl', ['$scope', '$routeParams', '$window', 'Supplier',
  function($scope, $routeParams, $window, Supplier) {
	Supplier.get({supplierId:$routeParams.supplierId},
	  function(supplierData) {
		$scope.supplier = supplierData;
	  },
	  function(err){
		  $window.location = '/app/index.html';
	});
	$scope.pageHeader = "Edit Supplier";
	$scope.isIdDisable = true;
    
    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };
      
    $scope.saveSupplier = function() {
    	Supplier.update({supplierId:$scope.supplier.id}, $scope.supplier, 
        function(){
    	  $scope.alerts = [{ type: 'success', msg: 'Supplier successfully updated.' }];
        },
        function(err){
          $scope.alerts = [{ type: 'danger', msg: 'Failed. Error:' + err.status}];
        });
    };
  }]);

koleksiNaiaControllers.controller('SuppOrderCtrl', [
  function() {
  }]);

