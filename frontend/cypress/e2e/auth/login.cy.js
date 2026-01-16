/// <reference types="cypress" />

describe('Login and Signup testing', () => {

  context('no Session', () => {
    beforeEach(() => {
      cy.get('body').wait()
    })

    it('should redirect to login on course', () => {
      cy.visit('http://localhost:5173/course')
      cy.location('pathname').should('eq', '/login')
    })

    it('should redirect to login on subpaths', () => {
      cy.visit('http://localhost:5173/course/1')
      cy.location('pathname').should('eq', '/login')
    })

    it('should redirect to login on profile', () => {
      cy.visit('http://localhost:5173/profile/preferences')
      cy.location('pathname').should('eq', '/login')
    })

  })

  context('invalid auth data', () => {
    beforeEach(() => {
      cy.visit('http://localhost:5173/login')
      cy.location('pathname').should('eq', '/login')
    })


    it('should show error on invalid inputs', () => {
      cy.get('input[name="email"]').type("test@testmail.com")
      cy.get('input[name="password"]').type("invalidpassword")
    })

    it('should show error on invalid inputs', () => {
      cy.get('input[name="email"]').type("nonexistingmail@aaa.com")
      cy.get('input[name="password"]').type("test123456789")
    })

    afterEach(() => {
      cy.get('button[type="submit"]').click()
      cy.get('#error').should('exist')
    })
  })

  context('valid auth data', () => {
    beforeEach(() => {
      cy.visit('http://localhost:5173/login')
      cy.location('pathname').should('eq', '/login')
    })


    it('should login in as normal user', () => {
      cy.get('input[name="email"]').type("test@testmail.com")
      cy.get('input[name="password"]').type("test1234567890")
      cy.get('button[type="submit"]').click()
      cy.location('pathname').should('eq', '/course')
    })

    it('should login in as normal user', () => {
      cy.get('input[name="email"]').type("test@testmail.com")
      cy.get('input[name="password"]').type("test1234567890")
      cy.get('button[type="submit"]').click()
      cy.location('pathname').should('eq', '/course')
    })

      it('should login in as instructor', () => {
      cy.get('input[name="email"]').type("instructor@testmail.com")
      cy.get('input[name="password"]').type("instructor123")
      cy.get('button[type="submit"]').click()
      cy.location('pathname').should('eq', '/course')
      cy.get("#my-courses")
    })

    afterEach(() => {
      cy.clearCookies()
    })
  })
})
