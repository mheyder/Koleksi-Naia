'use strict';

/* Controllers */

var OrderControllers = angular.module('orderController', []);

//***** ORDERS *****
OrderControllers.controller('OrderListCtrl', ['$scope', 'Order', 'Supplier', 'Customer',
  function($scope, Order, Supplier, Customer) {
    $scope.suppliers = Supplier.queryNameList();
    $scope.customers = Customer.queryNameList();
    $scope.sorts = [{value:'newest', text:'newest'},
	                {value:'oldest', text:'oldest'}];
    $scope.sort = $scope.sorts[0].value;
    $scope.isCollapsed = true;
    $scope.maxSize = 5;
    var monthInMilliseconds = 2629743000;
    
    $scope.clear = function() {
      $scope.endDate = new Date();
      $scope.startDate = new Date(new Date().getTime() - monthInMilliseconds);
      $scope.customerId = "";
      $scope.supplierId = "";
    };
    $scope.clear();
	
    $scope.search = function() {
      $scope.response = Order.search({
          page:$scope.currentPage, 
          sort:$scope.sort,
          startDate:$scope.startDate.getTime(),
          endDate:$scope.endDate.getTime(),
          customerId:$scope.customerId,
          supplierId:$scope.supplierId});
    };
    $scope.search();
    
    $scope.changePage = function(page) {
      $scope.currentPage = page;
      $scope.search();
    };
  }]);

OrderControllers.controller('OrderCreateCtrl', ['$scope', '$http', 'Order', 'Supplier', 'Customer',
  function($scope, $http, Order, Supplier, Customer) {
    $scope.suppliers = Supplier.query({detail:'namelist'});
    $scope.customers = Customer.query({detail:'namelist'});
    $scope.newOrders = [{date: new Date()}];
    $scope.maxDate = new Date();

    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };
    $scope.addRow = function() {
      $scope.newOrders.push({date: new Date()});
    };
    
    $scope.deleteRow = function(order) {
      $scope.newOrders.splice( $scope.newOrders.indexOf(order), 1 );
      if ($scope.newOrders.length == 0) $scope.addRow();
    };
    
    $scope.updatePrice = function(order) {
    	var index = $scope.newOrders.indexOf(order);
    	var cost = parseInt(order.cost);
    	var discount = 0;
    	if (order.customer != null) {
    		for(var i=0; i<$scope.customers.length; i++) {
    			if (order.customer.id == $scope.customers[i].id) {
    				discount = parseInt($scope.customers[i].discount); break;
    			}
    		}
    	};
    	$scope.newOrders[index].price = ((cost < 100) ? cost +12 : cost+15) - discount;
    };

    $scope.saveOrders = function() {
      $scope.submitted = true;
      if ($scope.createOrdersForm.$valid) {	
        Order.create($scope.newOrders, 
          function(){
  	        $scope.alerts = [{ type: 'success', msg: 'Orders successfully created.' }];
  	        $scope.newOrders = [{date: new Date()}];
  	        $scope.submitted = false;
          },
          function(err){
            // var allPropertyNames = Object.keys(err.config.headers);
            // $scope.alerts = [];
            // for (var j=0; j<allPropertyNames.length; j++) {
                // var name = allPropertyNames[j];
                // var value = err.config.headers;
                // $scope.alerts.push({ type: 'danger', msg: 'Failed. Error: ' + name +' : '+ value});
            // }

      	    $scope.alerts = [{ type: 'danger', msg: 'Failed. Error:' + err.status}];
        });
      } else {
    	  $scope.alerts = [{ type: 'danger', msg: 'Please filled the required inputs'}];
      };
    }; 
  }]);

OrderControllers.controller('OrderDetailCtrl', ['$scope', '$routeParams', '$window', 'Order',
  function($scope, $routeParams, $window, Order) {
    Order.get({orderId:$routeParams.orderId},
      function(orderData) {
        $scope.order = orderData;
      },
      function(err){
        //$window.location = '/app/index.html';
      });
    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };
		    
    $scope.saveOrder = function() {
      Order.update({orderId:$scope.order.id}, $scope.order, 
        function(){
          $scope.alerts = [{ type: 'success', msg: 'Order successfully updated.' }];
        },
        function(err){
          $scope.alerts = [{ type: 'danger', msg: 'Failed. Error:' + err.status}];
        });
    };
  }]);