describe('Authentication Flow', () => {
  describe('Login', () => {
    beforeEach(() => {
      cy.visit('/login');
    });

    it('should successfully log in and redirect', () => {
      cy.intercept('POST', '**/api/login', {
        statusCode: 200,
        body: 'fake-jwt-token-123',
        headers: { 'content-type': 'text/plain' },
      }).as('loginRequest');

      cy.get('input[formControlName="login"]').type('validUser');
      cy.get('input[formControlName="password"]').type('password123');
      cy.contains('button', 'Login').click();

      cy.wait('@loginRequest');

      cy.window().then((win) => {
        expect(win.localStorage.getItem('token')).to.eq('fake-jwt-token-123');
      });
      cy.location('pathname').should('eq', '/etudiants');
    });

    it('should show an error message on 401 Unauthorized', () => {
      cy.intercept('POST', '**/api/login', {
        statusCode: 401,
        body: { message: 'Login failed' },
      }).as('loginFail');

      cy.get('input[formControlName="login"]').type('wrongUser');
      cy.get('input[formControlName="password"]').type('wrongPassword');
      cy.contains('button', 'Login').click();

      cy.wait('@loginFail');
      cy.get('.error-message')
        .invoke('text')
        .invoke('trim')
        .should('eq', 'Login failed');
    });
  });

  describe('Register', () => {
    beforeEach(() => {
      cy.visit('/register');
    });

    it('should show validation errors when submitted empty', () => {
      cy.get('button[type="submit"]').click();
      cy.contains('First Name is required').should('be.visible');
      cy.contains('Last Name is required').should('be.visible');
      cy.contains('Login is required').should('be.visible');
      cy.contains('password is required').should('be.visible');
    });

    it('should register successfully, trigger alert, and redirect', () => {
      cy.intercept('POST', '**/api/register', {
        statusCode: 201,
        body: { message: 'User created' },
      }).as('registerRequest');

      cy.window().then((win) => {
        cy.stub(win, 'alert').as('alertStub');
      });

      cy.get('input[formControlName="firstName"]').type('John');
      cy.get('input[formControlName="lastName"]').type('Doe');
      cy.get('input[formControlName="login"]').type('johndoe');
      cy.get('input[formControlName="password"]').type('securepassword');

      cy.get('form').should('not.have.class', 'ng-invalid');
      cy.contains('button', 'Register').click();

      cy.wait('@registerRequest');
      cy.get('@alertStub').should('be.calledWithMatch', /SUCCESS/);
      cy.url().should('include', '/login');
    });
  });
});
