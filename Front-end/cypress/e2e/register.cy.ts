describe('Register Flow', () => {
  beforeEach(() => {
    cy.visit('/register');
  });

  it('should show validation errors when submitted empty', () => {
    // Submit an empty form
    cy.get('button[type="submit"]').click();

    cy.contains('First Name is required').should('be.visible');
    cy.contains('Last Name is required').should('be.visible');
    cy.contains('Login is required').should('be.visible');
    cy.contains('password is required').should('be.visible');
  });

  it('should register successfully, show alert, and redirect to login', () => {
    cy.intercept('POST', '**/api/register', {
      statusCode: 201,
      body: { message: 'User created' },
    }).as('registerRequest');

    cy.window().then((win) => {
      cy.stub(win, 'alert').as('alertStub');
    });

    // Fill and submit the form
    cy.get('input[formControlName="firstName"]').type('John');
    cy.get('input[formControlName="lastName"]').type('Doe');
    cy.get('input[formControlName="login"]').type('johndoe');
    cy.get('input[formControlName="password"]').type('securepassword');
    cy.get('button[type="submit"]').click();

    cy.wait('@registerRequest');

    cy.get('@alertStub').should('be.calledWithMatch', /SUCCESS/);
    cy.url().should('include', '/login');
  });
});
