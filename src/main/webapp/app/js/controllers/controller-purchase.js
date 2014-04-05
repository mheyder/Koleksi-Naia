'use strict';

/* PURCHASE Controllers */

var PurchaseControllers = angular.module('purchaseController', []);

PurchaseControllers.controller('PurchListCtrl', ['$scope', 'Supplier', 'Purchase',
  function($scope, Supplier, Purchase) {
    $scope.suppliers = Supplier.query({detail:'namelist'});
    $scope.maxSize = 5;
    var monthInMilliseconds = 2629743000;
    
    $scope.clear = function() {
      $scope.endDate = new Date();
      $scope.startDate = new Date(new Date().getTime() - monthInMilliseconds);
      $scope.supplierId = "";
    };
    $scope.clear();
    
    $scope.search = function() {
      $scope.response = Purchase.search({
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
PurchaseControllers.controller('PurchCreateCtrl', ['$scope', 'Supplier', 'Order', 'Collection', 'Purchase',
  function($scope, Supplier, Order, Collection, Purchase) {   
    $scope.createPage = true;
    $scope.maxDate = new Date();
    $scope.loadDetail = function() {
      $scope.suppliers = Supplier.query({detail:'unpurchased'});
      $scope.purchase = {date: new Date(), orderCount: 0, orderCost: 0, collectionCost: 0, otherCost: 0, totalCost: 0, info: "", orders: [], collections: []};
      $scope.orders = {};
      $scope.collections = {};
    };
    $scope.loadDetail();
    $scope.getOrdersAndCollections = function() {
      $scope.orders = Order.query({detail:'unpurchased', supplierId:$scope.supplierId});
      $scope.purchase = {date: new Date(), orderCount: 0, orderCost: 0, collectionCost: 0, otherCost: 0, totalCost: 0, info: "", supplier: {id: $scope.supplierId}, orders: [], collections: []};
      $scope.collections = Collection.query({detail:'unpurchased', supplierId:$scope.supplierId});
    };
    
    $scope.$watch('(purchase.orderCost * 1) + (purchase.collectionCost * 1) + (purchase.otherCost * 1)', function (value) {
        $scope.purchase.totalCost = value;
    });
    
    $scope.addOrder = function(flag, orderId, orderCost) {
      if (flag) {
        $scope.purchase.orders.push({id: orderId});
        $scope.purchase.orderCost += orderCost;
      } else {
        for (var i=0; i<$scope.purchase.orders.length; i++) {
          if ($scope.purchase.orders[i].id == orderId) {
            $scope.purchase.orders.splice(i,1); 
            $scope.purchase.orderCost -= orderCost;
            break;            
          }
        }
      };
      $scope.purchase.orderCount = $scope.purchase.orders.length;
    };
    
    $scope.addCollection = function(flag, collectionId, collectionCost) {
      if (flag) {
        $scope.purchase.collections.push({id: collectionId});
        $scope.purchase.collectionCost += collectionCost;       
      } else {
        for (var i=0; i<$scope.purchase.collections.length; i++) {
          if ($scope.purchase.collections[i].id == collectionId) {
            $scope.purchase.collections.splice(i,1); 
            $scope.purchase.collectionCost -= collectionCost;
            break;
          }
        }
      };
    };
    
    $scope.savePurchase = function() {
      Purchase.create($scope.purchase, 
        function(){
          $scope.alerts = [{ type: 'success', msg: 'Orders successfully purchased.' }];
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
  
PurchaseControllers.controller('PurchDetailCtrl', ['$scope', '$routeParams', '$window', 'Purchase',
  function($scope, $routeParams, $window, Purchase) { 
    Purchase.get({purchaseId:$routeParams.purchaseId},
      function(data) {
        $scope.purchase = data;
      },
      function(err){
        $window.location = '/app/index.html';
      });
  }]);
  
// * Edit Controller
PurchaseControllers.controller('PurchEditCtrl', ['$scope', '$routeParams', '$window', 'Order', 'Collection', 'Purchase',
  function($scope, $routeParams, $window, Order, Collection, Purchase) {
    $scope.editPage = true;
    $scope.maxDate = new Date();
    Purchase.get({purchaseId:$routeParams.purchaseId},
      function(data) {
        $scope.loadDetail(data);        
      },
      function(err){
        //$window.location = '/app/index.html';
      });
    $scope.loadDetail = function(data) {
      $scope.purchase = {id: data.id, date: data.date, supplier: {id: data.supplierId, name: data.supplierName}, orderCount: data.orderCount, orderCost: data.orderCost, collectionCost: data.collectionCost, otherCost: data.otherCost, totalCost: data.totalCost, info: data.info, orders: [], collections: []};
      $scope.orders = data.orders;
      $scope.collections = data.collections;
      $scope.availOrders = Order.query({detail:'unpurchased', supplierId:$scope.purchase.supplier.id});
      $scope.availCollections = Collection.query({detail:'unpurchased', supplierId:$scope.purchase.supplier.id});
    }; 
    
    $scope.$watch('(purchase.orderCost * 1) + (purchase.collectionCost * 1) + (purchase.otherCost * 1)', function (value) {
        $scope.purchase.totalCost = value;
    });
    
    $scope.removeOrder = function(order) {
      $scope.orders.splice( $scope.orders.indexOf(order), 1);
      $scope.availOrders.push(order);
      $scope.purchase.orderCost -= order.cost;
    };
    
    $scope.addOrder = function(order) {
      $scope.availOrders.splice( $scope.availOrders.indexOf(order), 1);
      $scope.orders.push(order);
      $scope.purchase.orderCost += order.cost;
    };
    
    $scope.removeCollection = function(collection) {
      $scope.collections.splice( $scope.collections.indexOf(collection), 1);
      $scope.availCollections.push(collection);
      $scope.purchase.collectionCost -= collection.cost;
    };
    
    $scope.addCollection = function(collection) {
      $scope.availCollections.splice( $scope.availCollections.indexOf(collection), 1);
      $scope.collections.push(collection);
      $scope.purchase.collectionCost += collection.cost;
    };
    
    $scope.collectOrders = function() {
      $scope.purchase.orders = [];
      for (var i=0; i<$scope.orders.length; i++) {
          $scope.purchase.orders.push({id: $scope.orders[i].id});
      }
      $scope.purchase.collections = [];
      for (var i=0; i<$scope.collections.length; i++) {
          $scope.purchase.collections.push({id: $scope.collections[i].id});
      }
      Purchase.update({purchaseId:$scope.purchase.id}, $scope.purchase, 
        function(data){
          $scope.alerts = [{ type: 'success', msg: 'Purchase Order successfully saved.' }];
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