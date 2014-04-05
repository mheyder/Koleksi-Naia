'use strict';


// Declare app level module which depends on filters, and services
angular.module('koleksiNaia', [
  'ngRoute',
  'koleksiNaia.filters',
  'koleksiNaia.services',
  'koleksiNaia.directives',
  'koleksiNaia.controllers',
  'ui.bootstrap',
  'dateParser',
  'dateParserDirective'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/dashboard', {templateUrl: 'partials/dashboard.html', controller: 'DashboardCtrl'});

  // ***** ORDERS *****   
  $routeProvider.when('/orders', {templateUrl: 'partials/orders/order-list.html', controller: 'OrderListCtrl'});
  $routeProvider.when('/orders/create', {templateUrl: 'partials/orders/order-create.html', controller: 'OrderCreateCtrl'});
  $routeProvider.when('/orders/:orderId', {templateUrl: 'partials/orders/order-detail.html', controller: 'OrderDetailCtrl'});
  $routeProvider.when('/orders/edit/:orderId', {templateUrl: 'partials/orders/order-form.html', controller: 'OrderDetailCtrl'});
  
  // ***** PURCHASE *****
  $routeProvider.when('/purchases', {templateUrl: 'partials/purchase/purchase-list.html', controller: 'PurchListCtrl'});
  $routeProvider.when('/purchases/create', {templateUrl: 'partials/purchase/purchase-form.html', controller: 'PurchCreateCtrl'});
  $routeProvider.when('/purchases/:purchaseId', {templateUrl: 'partials/purchase/purchase-detail.html', controller: 'PurchDetailCtrl'});
  $routeProvider.when('/purchases/edit/:purchaseId', {templateUrl: 'partials/purchase/purchase-form.html', controller: 'PurchEditCtrl'});
  
  // ***** COLLECTION *****
  $routeProvider.when('/collection', {templateUrl: 'partials/collection/collection-list.html', controller: 'CollListCtrl'});
  $routeProvider.when('/collection/create', {templateUrl: 'partials/collection/collection-form.html', controller: 'CollCreateCtrl'});
  $routeProvider.when('/collection/:collectionId', {templateUrl: 'partials/collection/collection-detail.html', controller: 'CollDetailCtrl'});
  $routeProvider.when('/collection/edit/:collectionId', {templateUrl: 'partials/collection/collection-form.html', controller: 'CollEditCtrl'});
  
  // ***** PAYMENT *****
  $routeProvider.when('/payments', {templateUrl: 'partials/payment/payment-list.html', controller: 'PaymListCtrl'});
  $routeProvider.when('/payments/create', {templateUrl: 'partials/payment/payment-form.html', controller: 'PaymCreateCtrl'});
  $routeProvider.when('/payments/:paymentId', {templateUrl: 'partials/payment/payment-detail.html', controller: 'PaymDetailCtrl'});
  $routeProvider.when('/payments/edit/:paymentId', {templateUrl: 'partials/payment/payment-form.html', controller: 'PaymEditCtrl'});
  
  // ***** SHIPPING *****
  $routeProvider.when('/shipping', {templateUrl: 'partials/shipping/shipping-list.html', controller: 'ShipListCtrl'});
  $routeProvider.when('/shipping/create', {templateUrl: 'partials/shipping/shipping-form.html', controller: 'ShipCreateCtrl'});
  $routeProvider.when('/shipping/:shippingId', {templateUrl: 'partials/shipping/shipping-detail.html', controller: 'ShipDetailCtrl'});
  $routeProvider.when('/shipping/edit/:shippingId', {templateUrl: 'partials/shipping/shipping-form.html', controller: 'ShipEditCtrl'});
  
  // ***** SUPPLIER *****
  $routeProvider.when('/suppliers', {templateUrl: 'partials/suppliers/supplier-list.html', controller: 'SuppListCtrl'}); 
  $routeProvider.when('/suppliers/create', {templateUrl: 'partials/suppliers/supplier-form.html', controller: 'SuppCreateCtrl'});  
  $routeProvider.when('/suppliers/:supplierId', {templateUrl: 'partials/suppliers/supplier-form.html', controller: 'SuppEditCtrl' });
  
  // ***** CUSTOMER *****
  $routeProvider.when('/customers', {templateUrl: 'partials/customers/customer-list.html', controller: 'CustListCtrl'});
  $routeProvider.when('/customers/create', {templateUrl: 'partials/customers/customer-form.html', controller: 'CustCreateCtrl'});
  $routeProvider.when('/customers/:customerId', {templateUrl: 'partials/customers/customer-form.html', controller: 'CustEditCtrl'});
  
  // ***** Default *****
  $routeProvider.otherwise({redirectTo: '/dashboard'});
}]);

angular.module('koleksiNaia.controllers', [
  'mainController',
  'orderController',
  'customerController',
  'supplierController',
  'purchaseController',
  'collectionController',
  'paymentController',
  'shippingController',
  'chieffancypants.loadingBar'
]);
