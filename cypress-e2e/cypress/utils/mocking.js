import {
  paginatedListOfTenNumbers,
  paginatedListOfTenNumbersPageTwo,
} from "./testUtils";

export function mockGetNumberList() {
  cy.server();
  cy.route({
    method: "GET",
    url: "**/api/v1/numbers?page=0&size=10",
    status: 200,
    response: paginatedListOfTenNumbers(),
    delay: 50,
    headers: {
      "X-Token": null,
    },
    onRequest: (xhr) => {},
    onResponse: (xhr) => {},
  }).as("getAllNumbers");

  cy.route({
    method: "GET",
    url: "**/api/v1/numbers?page=1&size=10",
    status: 200,
    response: paginatedListOfTenNumbersPageTwo(),
    delay: 50,
    headers: {
      "X-Token": null,
    },
    onRequest: (xhr) => {},
    onResponse: (xhr) => {},
  }).as("getAllNumbersPageTwo");
}
