/// <reference types="cypress" />

describe('Unimplemented endpoints', () => {

  context('', () => {
    beforeEach(() => {
      cy.visit('http://localhost:5173/login')
      cy.location('pathname').should('eq', '/login')
      cy.get('input[name="email"]').as("input-user").type("test@testmail.com")
      cy.get('input[name="password"]').as("input-pass").type("test1234567890")
      cy.get('button[type="submit"]').click()
      cy.location('pathname').should('eq', '/course')
    })


    it('get a 404 error', () => {
      cy.intercept('http://localhost:5173/dev').as('page')
      cy.visit("http://localhost:5173/dev", { failOnStatusCode: false })
      cy.wait("@page")
      cy.get('#error').should('exist')
    })


    afterEach(() => {
      cy.clearCookies()
    })
  })


})
