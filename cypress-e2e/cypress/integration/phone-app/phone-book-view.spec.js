import { mockGetNumberList, mockGetNumberListPageTwo } from "../../utils/mocking";

beforeEach(() => {
    Cypress.on('uncaught:exception', (err, runnable) => {
      return false
    });

    cy.server();
    mockGetNumberList();
    Cypress.on('window:before:load', (win) => {
      Object.defineProperty(win, 'self', {
        get: () => {
          return window.top
        }
      })
    })
    cy.visit('/')
});


it('Should check that the table renders 10 elements', () => {
  mockGetNumberList();
  cy.wait('@getAllNumbers');
  cy.get("#phoneTable")
  .find("tr")
  .then((row) => {
    expect(row.length-1).to.equal(10)
    cy.log(row.length);
  });
});


it('Should check that the table when the user clicks next it renders 10 elements', () => {
  cy.get('#next').click();
  cy.wait('@getAllNumbersPageTwo');
  cy.get("#phoneTable")
  .find("tr")
  .then((row) => {
    expect(row.length-1).to.equal(10)
    cy.log(row.length);
  });
});


it('Should check that the table when the user clicks previous it renders 10 elements', () => {
  mockGetNumberList();
  cy.wait('@getAllNumbers');
  cy.get('#previous').click();
  cy.get("#phoneTable")
  .find("tr")
  .then((row) => {
    expect(row.length-1).to.equal(10)
    cy.log(row.length);
  });
});

it('Should click on dark mode button and backround changes to dark',() =>{
  cy.get('#toggleViewMode').click();
})

