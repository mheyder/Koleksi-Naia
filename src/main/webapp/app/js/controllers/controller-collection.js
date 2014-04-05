'use strict';

/* PURCHASE-COLLECTION Controllers */

var CollectionControllers = angular.module('collectionController', []);

// * List Controller 
CollectionControllers.controller('CollListCtrl', ['$scope', 'Supplier', 'Collection',
  function($scope, Supplier, Collection) {
    $scope.suppliers = Supplier.query();
    $scope.maxSize = 5;
    var monthInMilliseconds = 2629743000;
    
    $scope.clear = function() {
      $scope.endDate = new Date();
      $scope.startDate = new Date(new Date().getTime() - monthInMilliseconds);
      $scope.supplierId = "";
    };
    $scope.clear();
    
    $scope.search = function() {
      $scope.response = Collection.search({
          page:$scope.currentPage,
          startDate:$scope.startDate.getTime(),
          endDate:$scope.endDate.getTime(),
          supplierId:$scope.supplierId});
    };
    $scope.search();
    
    $scope.changePage = function(page) {
      $scope.currentPage = page;
      $scope.search();
    };
  }]);
  
// * Create Controller
CollectionControllers.controller('CollCreateCtrl', ['$scope', 'Supplier', 'Order', 'Collection',
  function($scope, Supplier, Order, Collection) {   
    $scope.createPage = true;
    $scope.maxDate = new Date();
    $scope.loadDetail = function() {
      $scope.suppliers = Supplier.query({detail:'uncollected'});
      $scope.collection = {date: new Date(), totalOrder: 0, cost: 0, info: "", orders: []};
      $scope.orders = {};
    };
    $scope.loadDetail();
    $scope.getOrders = function() {
      $scope.orders = Order.query({detail:'uncollected', supplierId:$scope.supplierId});
      $scope.collection = {date: new Date(), totalOrder: 0, cost: 0, info: "", supplier: {id: $scope.supplierId}, orders: []};
    };
    
    $scope.addOrder = function(flag, OrderId) {
      if (flag) {
        $scope.collection.orders.push({id: OrderId});        
      } else {
        for (var i=0; i<$scope.collection.orders.length; i++) {
          if ($scope.collection.orders[i].id == OrderId) {
            $scope.collection.orders.splice(i,1); break;
          }
        }
      };
      $scope.collection.totalOrder = $scope.collection.orders.length;
    };
    
    $scope.collectOrders = function() {
      Collection.create($scope.collection, 
        function(){
          $scope.alerts = [{ type: 'success', msg: 'Orders successfully collected.' }];
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
CollectionControllers.controller('CollDetailCtrl', ['$scope', '$routeParams', '$window', 'Collection',
  function($scope, $routeParams, $window, Collection) { 
    Collection.get({collectionId:$routeParams.collectionId},
      function(data) {
        $scope.collection = data;
      },
      function(err){
        $window.location = '/app/index.html';
      });
  }]);
  
// * Edit Controller
CollectionControllers.controller('CollEditCtrl', ['$scope', '$routeParams', '$window', 'Order', 'Collection',
  function($scope, $routeParams, $window, Order, Collection) {
    $scope.editPage = true;
    $scope.maxDate = new Date();
    Collection.get({collectionId:$routeParams.collectionId},
      function(data) {
        $scope.loadDetail(data);        
      },
      function(err){
        //$window.location = '/app/index.html';
      });
    $scope.loadDetail = function(data) {
      $scope.collection = {id: data.id, date: data.date, supplier: {id: data.supplierId, name: data.supplierName}, totalOrder: data.totalOrder, cost: data.cost, info: data.info, orders: []};
      $scope.orders = data.orders;
      $scope.availOrders = Order.query({detail:'uncollected', supplierId:$scope.collection.supplier.id});
    };
    
    
    $scope.removeOrder = function(order) {
      $scope.orders.splice( $scope.orders.indexOf(order), 1);
      $scope.availOrders.push(order);
    };
    
    $scope.addOrder = function(order) {
      $scope.availOrders.splice( $scope.availOrders.indexOf(order), 1);
      $scope.orders.push(order);
    };
    
    $scope.collectOrders = function() {
      $scope.collection.orders = [];
      for (var i=0; i<$scope.orders.length; i++) {
          $scope.collection.orders.push({id: $scope.orders[i].id});
      }
      Collection.update({collectionId:$scope.collection.id}, $scope.collection, 
        function(data){
          $scope.alerts = [{ type: 'success', msg: 'Collect Orders successfully saved.' }];
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