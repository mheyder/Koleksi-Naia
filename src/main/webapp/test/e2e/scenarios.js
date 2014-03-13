'use strict';

/* https://github.com/angular/protractor/blob/master/docs/getting-started.md */

describe('Koleksi Naia Order', function() {

  it('should redirect index.html to index.html#/phones', function() {
    browser().navigateTo('app/index.html');
    expect(browser().location().url()).toBe('/dashboard');
  });
  
  describe('Supplier list view', function() {

    beforeEach(function() {
      browser().navigateTo('app/index.html#/suppliers');
    });
    
    it('should render phone specific links', function() {
      input('query').enter('nexus');
      element('.phones li a').click();
      expect(browser().location().url()).toBe('/phones/nexus-s');
    });
  });

  describe('view1', function() {

    beforeEach(function() {
      browser.get('index.html#/view1');
    });


    it('should render view1 when user navigates to /view1', function() {
      expect(element.all(by.css('[ng-view] p')).first().getText()).
        toMatch(/partial for view 1/);
    });

  });


  describe('view2', function() {

    beforeEach(function() {
      browser.get('index.html#/view2');
    });


    it('should render view2 when user navigates to /view2', function() {
      expect(element.all(by.css('[ng-view] p')).first().getText()).
        toMatch(/partial for view 2/);
    });

  });
});
