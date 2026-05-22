describe('Login and Student Flow', () => {
  beforeEach(() => {
    cy.intercept('POST', '**/api/login', {
      statusCode: 200,
      body: 'fake-token-123',
      headers: { 'content-type': 'text/plain' },
    }).as('loginRequest');

    cy.intercept('GET', '**/api/etudiants', {
      statusCode: 200,
      body: [
        { id: 1, name: 'John Doe' },
        { id: 2, name: 'Tony Fred' },
      ],
    }).as('getEtudiants');

    cy.visit('/login');
  });

  it('should successfully log in, redirect, and load the student list', () => {
    cy.get('input[formControlName="login"]').type('User123');
    cy.get('input[formControlName="password"]').type('password123');
    cy.get('button[type="submit"]').click();

    cy.wait('@loginRequest');

    cy.window().then((win) => {
      win.localStorage.setItem('token', 'fake-token-123');
    });

    cy.wait('@getEtudiants');

    cy.url().should('include', '/etudiants');
    cy.get('table').should('be.visible');
  });

  it('should show an error message on failed login', () => {
    cy.intercept('POST', '**/api/login', {
      statusCode: 401,
      body: { message: 'Login failed' },
    }).as('loginFail');

    cy.get('input[formControlName="login"]').type('login');
    cy.get('input[formControlName="password"]').type('wrongpassword');
    cy.get('button[type="submit"]').click();

    cy.wait('@loginFail');
    cy.get('.error-message')
      .should('be.visible')
      .and('contain', 'Login failed');
  });

  it('can visit the dashboard directly if token exists', () => {
    localStorage.setItem('token', 'fake-token-123');
    cy.visit('/');
    cy.get('body').should('exist');
  });
});
