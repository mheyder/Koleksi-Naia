'use strict';

/* jasmine specs for controllers go here */

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

  describe('SuppListCtrl', function(){
    var scope, ctrl, $httpBackend;

    beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
      $httpBackend = _$httpBackend_;
      $httpBackend.expectGET('../rest/suppliers').
          respond([{id:'SR', name:'Strawberry'}, {id:'Pink', name:'Pink'}]);

      scope = $rootScope.$new();
      ctrl = $controller('SuppListCtrl', {$scope: scope});
    }));


    it('should show list of suppliers with 2 suppliers fetched from xhr', function() {
      expect(scope.suppliers).toEqualData([]);
      $httpBackend.flush();

      expect(scope.suppliers).toEqualData(
          [{id:'SR', name:'Strawberry'}, {id:'Pink', name:'Pink'}]);
    });
  });
});
