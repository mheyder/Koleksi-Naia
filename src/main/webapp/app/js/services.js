'use strict';

/* Services */

var koleksiNaiaServices = angular.module('koleksiNaia.services', ['ngResource']);

koleksiNaiaServices.value('version', '0.1');

koleksiNaiaServices.factory('Order', ['$resource',
 function($resource){
   return $resource('../rest/orders/:orderId', {}, {
      query: {method:'GET', isArray:true},
      search: {method:'GET'},
      create: {method:'POST'},
      update: {method:'PUT'}
   });
 }]);

koleksiNaiaServices.factory('Customer', ['$resource',
  function($resource){
    return $resource('../rest/customers/:customerId', {}, {
      query: {method:'GET', isArray:true},
      queryNameList: {method:'GET', params:{detail:'namelist'}, isArray:true, ignoreLoadingBar: true},
      create: {method:'POST'},
      update: {method:'PUT'}
    });
  }]);
  
koleksiNaiaServices.factory('Supplier', ['$resource',
  function($resource){
    return $resource('../rest/suppliers/:supplierId', {}, {
      query: {method:'GET', isArray:true},
      queryNameList: {method:'GET', params:{detail:'namelist'}, isArray:true, ignoreLoadingBar: true},
      create: {method:'POST'},
      update: {method:'PUT'}
    });
  }]);

koleksiNaiaServices.factory('Purchase', ['$resource',
  function($resource){
    return $resource('../rest/purchases/:purchaseId', {}, {
      query: {method:'GET', isArray:true},
      search: {method:'GET'},
      create: {method:'POST'},
      update: {method:'PUT'}
    });
  }]);
  
koleksiNaiaServices.factory('Collection', ['$resource',
  function($resource){
    return $resource('../rest/collection/:collectionId', {}, {
      query: {method:'GET', isArray:true},
      search: {method:'GET'},
      create: {method:'POST'},
      update: {method:'PUT'}
    });
  }]);
  
koleksiNaiaServices.factory('Payment', ['$resource',
  function($resource){
    return $resource('../rest/payments/:paymentId', {}, {
      query: {method:'GET', isArray:true},
      search: {method:'GET'},
      create: {method:'POST'},
      update: {method:'PUT'}
    });
  }]);
  
koleksiNaiaServices.factory('Shipping', ['$resource',
  function($resource){
    return $resource('../rest/shipping/:shippingId', {}, {
      query: {method:'GET', isArray:true},
      search: {method:'GET'},
      create: {method:'POST'},
      update: {method:'PUT'}
    });
  }]);
