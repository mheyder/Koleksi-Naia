'use strict';

/* PAYMENT Controllers */

var PaymentControllers = angular.module('paymentController', []);

PaymentControllers.controller('PaymListCtrl', ['$scope', 'Customer', 'Payment',
  function($scope, Customer, Payment) {
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
      $scope.response = Payment.search({
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
PaymentControllers.controller('PaymCreateCtrl', ['$scope', 'Customer', 'Order', 'Shipping', 'Payment',
  function($scope, Customer, Order, Shipping, Payment) {   
    $scope.createPage = true;
    $scope.maxDate = new Date();
    $scope.loadDetail = function() {
      $scope.customers = Customer.query({detail:'unpaid'});
      $scope.payment = {date: new Date(), orderCount: 0, orderPrice: 0, shippingPrice: 0, otherPrice: 0, totalPrice: 0, info: "", orders: [], shippings: []};
      $scope.orders = {};
      $scope.shippings = {};
    };
    $scope.loadDetail();
    $scope.getOrdersAndShippings = function() {
      $scope.orders = Order.query({detail:'unpaid', customerId:$scope.customerId});
      $scope.payment = {date: new Date(), orderCount: 0, orderPrice: 0, shippingPrice: 0, otherPrice: 0, totalPrice: 0, info: "", customer: {id: $scope.customerId},orders: [], shippings: []};
      $scope.shippings = Shipping.query({detail:'unpaid', customerId:$scope.customerId});
    };
    
    $scope.$watch('(payment.orderPrice * 1) + (payment.shippingPrice * 1) + (payment.otherPrice * 1)', function (value) {
        $scope.payment.totalPrice = value;
    });
    
    $scope.addOrder = function(flag, orderId, orderPrice) {
      if (flag) {
        $scope.payment.orders.push({id: orderId});
        $scope.payment.orderPrice += orderPrice;
      } else {
        for (var i=0; i<$scope.payment.orders.length; i++) {
          if ($scope.payment.orders[i].id == orderId) {
            $scope.payment.orders.splice(i,1); 
            $scope.payment.orderPrice -= orderPrice;
            break;            
          }
        }
      };
      $scope.payment.orderCount = $scope.payment.orders.length;
    };
    
    $scope.addShipping = function(flag, shippingId, shippingPrice) {
      if (flag) {
        $scope.payment.shippings.push({id: shippingId});
        $scope.payment.shippingPrice += shippingPrice;       
      } else {
        for (var i=0; i<$scope.payment.shippings.length; i++) {
          if ($scope.payment.shippings[i].id == shippingId) {
            $scope.payment.shippings.splice(i,1); 
            $scope.payment.shippingPrice -= shippingPrice;
            break;
          }
        }
      };
    };
    
    $scope.savePayment = function() {
      Payment.create($scope.payment, 
        function(){
          $scope.alerts = [{ type: 'success', msg: 'Customer orders successfully paid.' }];
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
  
PaymentControllers.controller('PaymDetailCtrl', ['$scope', '$routeParams', '$window', 'Payment',
  function($scope, $routeParams, $window, Payment) { 
    Payment.get({paymentId:$routeParams.paymentId},
      function(data) {
        $scope.payment = data;
      },
      function(err){
        $window.location = '/app/index.html';
      });
  }]);
  
// * Edit Controller
PaymentControllers.controller('PaymEditCtrl', ['$scope', '$routeParams', '$window', 'Order', 'Shipping', 'Payment',
  function($scope, $routeParams, $window, Order, Shipping, Payment) {
    $scope.editPage = true;
    $scope.maxDate = new Date();
    Payment.get({paymentId:$routeParams.paymentId},
      function(data) {
        $scope.loadDetail(data);        
      },
      function(err){
        $window.location = '/app/index.html';
      });
    $scope.loadDetail = function(data) {
      $scope.payment = {id: data.id, date: data.date, customer: {id: data.customerId, name: data.customerName}, orderCount: data.orderCount, orderPrice: data.orderPrice, shippingPrice: data.shippingPrice, otherPrice: data.otherPrice, totalPrice: data.totalPrice, info: data.info, orders: [], shippings: []};
      $scope.orders = data.orders;
      $scope.shippings = data.shippings;
      $scope.availOrders = Order.query({detail:'unpaid', customerId:$scope.payment.customer.id});
      $scope.availShippings = Shipping.query({detail:'unpaid', customerId:$scope.payment.customer.id});
    }; 
    
    $scope.$watch('(payment.orderPrice * 1) + (payment.shippingPrice * 1) + (payment.otherPrice * 1)', function (value) {
        $scope.payment.totalPrice = value;
    });
    
    $scope.removeOrder = function(order) {
      $scope.orders.splice( $scope.orders.indexOf(order), 1);
      $scope.availOrders.push(order);
      $scope.payment.orderPrice -= order.price;
    };
    
    $scope.addOrder = function(order) {
      $scope.availOrders.splice( $scope.availOrders.indexOf(order), 1);
      $scope.orders.push(order);
      $scope.payment.orderPrice += order.price;
    };
    
    $scope.removeShipping = function(shipping) {
      $scope.shippings.splice( $scope.shippings.indexOf(shipping), 1);
      $scope.availShippings.push(shipping);
      $scope.payment.shippingPrice -= shipping.price;
    };
    
    $scope.addShipping = function(shipping) {
      $scope.availShippings.splice( $scope.availShippings.indexOf(shipping), 1);
      $scope.shippings.push(shipping);
      $scope.payment.shippingPrice += shipping.price;
    };
    
    $scope.savePayment = function() {
      $scope.payment.orders = [];
      for (var i=0; i<$scope.orders.length; i++) {
          $scope.payment.orders.push({id: $scope.orders[i].id});
      }
      $scope.payment.shippings = [];
      for (var i=0; i<$scope.shippings.length; i++) {
          $scope.payment.shippings.push({id: $scope.shippings[i].id});
      }
      Payment.update({paymentId:$scope.payment.id}, $scope.payment, 
        function(data){
          $scope.alerts = [{ type: 'success', msg: 'Customer payment successfully saved.' }];
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
