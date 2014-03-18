'use strict';

/* Services */

var koleksiNaiaServices = angular.module('koleksiNaia.services', ['ngResource']);

koleksiNaiaServices.value('version', '0.1');

koleksiNaiaServices.factory('Order', ['$resource',
 function($resource){
   return $resource('../rest/orders/:orderId', {}, {
     query: {method:'GET', isArray:true},
     create: {method:'POST'},
     update: {method:'PUT'}
   });
 }]);

koleksiNaiaServices.factory('Customer', ['$resource',
  function($resource){
    return $resource('../rest/customers/:customerId', {}, {
      query: {method:'GET', isArray:true},
      create: {method:'POST'},
      update: {method:'PUT'}
    });
  }]);
  
koleksiNaiaServices.factory('Supplier', ['$resource',
  function($resource){
    return $resource('../rest/suppliers/:supplierId', {}, {
      query: {method:'GET', params:{supplierId:''}, isArray:true},
      create: {method:'POST'},
      update: {method:'PUT'}
    });
  }]);

// Demonstrate how to register services
// In this case it is a simple value service.
//angular.module('koleksiNaia.services', []).
//  value('version', '0.1');
