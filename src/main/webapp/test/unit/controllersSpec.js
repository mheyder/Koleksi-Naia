'use strict';

/* jasmine specs for controllers go here */

// ***** SUPPLIER MODULE *****
describe('Supplier Controllers', function(){
	
  beforeEach(function(){
    this.addMatchers({
      toEqualData: function(expected) {
        return angular.equals(this.actual, expected);
      }
    });
  });
  
  beforeEach(module('koleksiNaia'));
  beforeEach(module('koleksiNaia.controllers'));

  // *** SUPPLIER LIST CONTROLLER ***
  describe('Supplier List Controller', function(){
    var scope, ctrl, $httpBackend;

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
      $httpBackend = _$httpBackend_;
      $httpBackend.expectGET('../rest/suppliers').
          respond([{id:'SR', name:'Strawberry'}, {id:'Pink', name:'Pink'}]);

      scope = $rootScope.$new();
      ctrl = $controller('SuppListCtrl', {$scope: scope});
    }));

    it('should show list of suppliers with 2 suppliers fetched from mockup', function() {
      expect(scope.suppliers).toEqualData([]);
      $httpBackend.flush();

      expect(scope.suppliers).toEqualData(
          [{id:'SR', name:'Strawberry'}, {id:'Pink', name:'Pink'}]);
    });
  });
  
  //*** SUPPLIER CREATE CONTROLLER ***
  describe('Supplier Create Controller', function(){
    var scope, ctrl, $httpBackend;

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
      $httpBackend = _$httpBackend_;
      scope = $rootScope.$new();
      ctrl = $controller('SuppCreateCtrl', {$scope: scope});
    }));

    it('should show the Edit Supplier header and disabled the ID textbox', function() {
    	expect(scope.pageHeader).toBe('New Supplier');
    	expect(scope.isIdDisable).toBeFalsy();
    	expect(scope.suplier).toEqualData();
    });
    
    it('should show success message when the supplier was created successfully', function() {
      $httpBackend.expectPOST('../rest/suppliers', {}).respond(201);
      scope.saveSupplier();
      $httpBackend.flush();
      expect(scope.alerts[0].msg).toBe('Supplier successfully created.');
    });
    
    it('should show error message when the supplier was failed to create', function() {
        $httpBackend.expectPOST('../rest/suppliers', {}).respond(400);
        scope.saveSupplier();
        $httpBackend.flush();
        expect(scope.alerts[0].msg).toContain('Failed. Error:');
    });    
  });
  
//*** SUPPLIER EDIT CONTROLLER ***
  describe('Supplier Edit Controller', function(){
    var scope, ctrl, $httpBackend, routeParams;

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $routeParams) {
      $httpBackend = _$httpBackend_;
      $httpBackend.expectGET('../rest/suppliers/SR').
        respond(200, {id:'SR', name:'Strawberry'});      
    	
      scope = $rootScope.$new();
      routeParams = {supplierId:'SR'}
      ctrl = $controller('SuppEditCtrl', {$scope: scope, $routeParams:routeParams});
    }));

    it('should show the Edit Supplier header and disabled the ID textbox', function() {
    	expect(scope.pageHeader).toBe('Edit Supplier');
    	expect(scope.isIdDisable).toBeTruthy();
    });

    it('should show supplier detail as defined', function() {
      $httpBackend.flush();
      expect(scope.supplier).toEqualData({id:'SR', name:'Strawberry'});
    });
    
    it('should show success message when the supplier was saved successfully', function() {
      $httpBackend.flush();
      $httpBackend.expectPUT('../rest/suppliers/SR', {id:'SR', name:'Strawberry'}).respond(201);
      scope.saveSupplier();
      $httpBackend.flush();
      expect(scope.alerts[0].msg).toBe('Supplier successfully updated.');
    });
    
    it('should show error message when the supplier was failed to create', function() {
    	$httpBackend.flush();
        $httpBackend.expectPUT('../rest/suppliers/SR', {id:'SR', name:'Strawberry'}).respond(400);
        scope.saveSupplier();
        $httpBackend.flush();
        expect(scope.alerts[0].msg).toContain('Failed. Error:');
    });
    
  });
});

//***** CUSTOMER MODULE *****
describe('Customer Controllers', function(){
	
  beforeEach(function(){
    this.addMatchers({
      toEqualData: function(expected) {
        return angular.equals(this.actual, expected);
      }
    });
  });
  
  beforeEach(module('koleksiNaia'));
  beforeEach(module('koleksiNaia.controllers'));

  // *** CUSTOMER LIST CONTROLLER ***
  describe('Customer List Controller', function(){
    var scope, ctrl, $httpBackend;

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
      $httpBackend = _$httpBackend_;
      $httpBackend.expectGET('../rest/customers').
          respond([{id:'AAN', name:'aan'}, {id:'POPPY', name:'poppy'}]);

      scope = $rootScope.$new();
      ctrl = $controller('CustListCtrl', {$scope: scope});
    }));

    it('should show list of customers with 2 customers fetched from mockup', function() {
      expect(scope.customers).toEqualData([]);
      $httpBackend.flush();

      expect(scope.customers).toEqualData(
          [{id:'AAN', name:'aan'}, {id:'POPPY', name:'poppy'}]);
    });
  });
  
  //*** CUSTOMER CREATE CONTROLLER ***
  describe('Customer Create Controller', function(){
    var scope, ctrl, $httpBackend;

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
      $httpBackend = _$httpBackend_;
      scope = $rootScope.$new();
      ctrl = $controller('CustCreateCtrl', {$scope: scope});
    }));

    it('should show the New Customer header and disabled the ID textbox', function() {
    	expect(scope.pageHeader).toBe('New Customer');
    	expect(scope.isIdDisable).toBeFalsy();
    	expect(scope.customer).toEqualData({});
    });
    
    it('should show success message when the customer was created successfully', function() {
      $httpBackend.expectPOST('../rest/customers', {}).respond(201);
      scope.saveCustomer();
      $httpBackend.flush();
      expect(scope.alerts[0].msg).toBe('Customer successfully created.');
    });
    
    it('should show error message when the customer was failed to create', function() {
        $httpBackend.expectPOST('../rest/customers', {}).respond(400);
        scope.saveCustomer();
        $httpBackend.flush();
        expect(scope.alerts[0].msg).toContain('Failed. Error:');
    });    
  });
  
//*** CUSTOMER EDIT CONTROLLER ***
  describe('Customer Edit Controller', function(){
    var scope, ctrl, $httpBackend, routeParams;

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $routeParams) {
      $httpBackend = _$httpBackend_;
      $httpBackend.expectGET('../rest/customers/AAN').
        respond(200, {id:'AAN', name:'aan'});      
    	
      scope = $rootScope.$new();
      routeParams = {customerId:'AAN'}
      ctrl = $controller('CustEditCtrl', {$scope: scope, $routeParams:routeParams});
    }));

    it('should show the Edit Customer header and disabled the ID textbox', function() {
    	expect(scope.pageHeader).toBe('Edit Customer');
    	expect(scope.isIdDisable).toBeTruthy();
    });

    it('should show customer detail as defined', function() {
      $httpBackend.flush();
      expect(scope.customer).toEqualData({id:'AAN', name:'aan'});
    });
    
    it('should show success message when the customer was saved successfully', function() {
      $httpBackend.flush();
      $httpBackend.expectPUT('../rest/customers/AAN', {id:'AAN', name:'aan'}).respond(201);
      scope.saveCustomer();
      $httpBackend.flush();
      expect(scope.alerts[0].msg).toBe('Customer successfully updated.');
    });
    
    it('should show error message when the customer was failed to create', function() {
    	$httpBackend.flush();
        $httpBackend.expectPUT('../rest/customers/AAN', {id:'AAN', name:'aan'}).respond(400);
        scope.saveCustomer();
        $httpBackend.flush();
        expect(scope.alerts[0].msg).toContain('Failed. Error:');
    });
    
  });
});
