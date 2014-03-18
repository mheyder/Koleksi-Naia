'use strict';


// Declare app level module which depends on filters, and services
angular.module('koleksiNaia', [
  'ngRoute',
  'koleksiNaia.filters',
  'koleksiNaia.services',
  'koleksiNaia.directives',
  'koleksiNaia.controllers',
  'ui.bootstrap'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/dashboard', {templateUrl: 'partials/dashboard.html', controller: 'DashboardCtrl'});

  // ***** ORDERS ***** 
  $routeProvider.when('/orders/create', {templateUrl: 'partials/orders/order-create.html', controller: 'OrderCreateCtrl'});
  $routeProvider.when('/orders/search', {templateUrl: 'partials/orders/order-list.html', controller: 'OrderListCtrl'});
  $routeProvider.when('/orders/:orderId', {templateUrl: 'partials/orders/order-detail.html', controller: 'OrderEditCtrl'});
  
  // ***** PURCHASE and COLLECTION *****
  $routeProvider.when('/purchase-collection', {templateUrl: 'partials/suppliers/purchase-collection-supplier-list.html', controller: 'SuppListCtrl'});
  $routeProvider.when('/purchase-collection/:supplierId', {templateUrl: 'partials/suppliers/purchase-collection-list.html', controller: 'SuppOrderCtrl'});
  
  // ***** Payment and Shipping *****
  $routeProvider.when('/payment-shipping', {templateUrl: 'partials/customers/payment-shipping-customer-list.html', controller: 'CustListCtrl'});
  $routeProvider.when('/payment-shipping/:customerId', {templateUrl: 'partials/customers/payment-shipping-list.html', controller: 'CustOrderCtrl'});
  
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

