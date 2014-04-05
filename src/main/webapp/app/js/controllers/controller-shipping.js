'use strict';

/* SHIPPING Controllers */

var ShippingControllers = angular.module('shippingController', []);

// * List Controller 
ShippingControllers.controller('ShipListCtrl', ['$scope', 'Customer', 'Shipping',
  function($scope, Customer, Shipping) {
    $scope.customers = Customer.query({detail:'namelist'});
    $scope.maxSize = 5;
    var monthInMilliseconds = 2629743000;
    
    $scope.clear = function() {
      $scope.endDate = new Date();
      $scope.startDate = new Date(new Date().getTime() - monthInMilliseconds);
      $scope.customerId = "";
    };
    $scope.clear();
    
    $scope.search = function() {
      $scope.response = Shipping.search({
          page:$scope.currentPage,
          startDate:$scope.startDate.getTime(),
          endDate:$scope.endDate.getTime(),
          customerId:$scope.customerId});
    };
    $scope.search();
    
    $scope.changePage = function(page) {
      $scope.currentPage = page;
      $scope.search();
    };
  }]);
  
// * Create Controller
ShippingControllers.controller('ShipCreateCtrl', ['$scope', 'Customer', 'Order', 'Shipping',
  function($scope, Customer, Order, Shipping) {   
    $scope.createPage = true;
    $scope.maxDate = new Date();
    $scope.loadDetail = function() {
      $scope.customers = Customer.query({detail:'unshipped'});
      $scope.shipping = {date: new Date(), dropship: "", address: "", orderCount: 0, price: 0, info: "", orders: []};
      $scope.orders = {};
    };
    $scope.loadDetail();
    $scope.getOrders = function() {
      $scope.orders = Order.query({detail:'unshipped', customerId:$scope.customerId});
      $scope.shipping = {date: new Date(), dropship: "", address: "", orderCount: 0, price: 0, info: "", customer: {id: $scope.customerId}, orders: []};
    };
    
    $scope.addOrder = function(flag, OrderId) {
      if (flag) {
        $scope.shipping.orders.push({id: OrderId});        
      } else {
        for (var i=0; i<$scope.shipping.orders.length; i++) {
          if ($scope.shipping.orders[i].id == OrderId) {
            $scope.shipping.orders.splice(i,1); break;
          }
        }
      };
      $scope.shipping.orderCount = $scope.shipping.orders.length;
    };
    
    $scope.saveShipping = function() {
      Shipping.create($scope.shipping, 
        function(){
          $scope.alerts = [{ type: 'success', msg: 'Orders successfully shipped.' }];
          $scope.loadDetail();
        },
        function(err){
          $scope.alerts = [{ type: 'danger', msg: 'Failed. Error:' + err.status}];
        });
    };
    
    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };    
  }]);
  
// * Detail Controller
ShippingControllers.controller('ShipDetailCtrl', ['$scope', '$routeParams', '$window', 'Shipping',
  function($scope, $routeParams, $window, Shipping) { 
    Shipping.get({shippingId:$routeParams.shippingId},
      function(data) {
        $scope.shipping = data;
      },
      function(err){
        $window.location = '/app/index.html';
      });
  }]);
  
// * Edit Controller
ShippingControllers.controller('ShipEditCtrl', ['$scope', '$routeParams', '$window', 'Order', 'Shipping',
  function($scope, $routeParams, $window, Order, Shipping) {
    $scope.editPage = true;
    $scope.maxDate = new Date();
    Shipping.get({shippingId:$routeParams.shippingId},
      function(data) {
        $scope.loadDetail(data);        
      },
      function(err){
        //$window.location = '/app/index.html';
      });
    $scope.loadDetail = function(data) {
      $scope.shipping = {id: data.id, date: data.date, dropship: data.dropship, address: data.address, orderCount: data.orderCount, price: data.price, info: data.info, customer: {id: data.customerId, name: data.customerName}, orders: []};
      $scope.orders = data.orders;
      $scope.availOrders = Order.query({detail:'unshipped', customerId:$scope.shipping.customer.id});
    };
    
    
    $scope.removeOrder = function(order) {
      $scope.orders.splice( $scope.orders.indexOf(order), 1);
      $scope.availOrders.push(order);
    };
    
    $scope.addOrder = function(order) {
      $scope.availOrders.splice( $scope.availOrders.indexOf(order), 1);
      $scope.orders.push(order);
    };
    
    $scope.saveShipping = function() {
      $scope.shipping.orders = [];
      for (var i=0; i<$scope.orders.length; i++) {
          $scope.shipping.orders.push({id: $scope.orders[i].id});
      }
      Shipping.update({shippingId:$scope.shipping.id}, $scope.shipping, 
        function(data){
          $scope.alerts = [{ type: 'success', msg: 'Shipping Order successfully saved.' }];
          $scope.loadDetail(data);
        },
        function(err){
          $scope.alerts = [{ type: 'danger', msg: 'Failed. Error:' + err.status}];
        });
    };
    
    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };    
    
  }]);