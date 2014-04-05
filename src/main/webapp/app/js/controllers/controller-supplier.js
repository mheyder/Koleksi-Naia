'use strict';

/* Controllers */

var SupplierControllers = angular.module('supplierController', []);

// ***** SUPPLIER *****
// *SUPP: supplier list page controller
SupplierControllers.controller('SuppListCtrl', ['$scope', 'Supplier',
  function($scope, Supplier) {
    $scope.suppliers = Supplier.query();
  }]);

// *SUPP: supplier create controller
//        handles the creation of new supplier
SupplierControllers.controller('SuppCreateCtrl', ['$scope', '$window', 'Supplier',
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
SupplierControllers.controller('SuppEditCtrl', ['$scope', '$routeParams', '$window', 'Supplier',
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



