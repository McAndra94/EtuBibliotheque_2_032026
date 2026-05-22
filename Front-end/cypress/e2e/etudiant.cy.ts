describe('Etudiant Management Flow', () => {
  beforeEach(() => {
    // Force the token first
    cy.window().then((win) => {
      win.localStorage.setItem('token', 'fake-token-123');
    });

    // Define interceptor, then visit the page
    cy.intercept('GET', '**/api/etudiants', {
      statusCode: 200,
      body: [{ id: 1, firstName: 'John', lastName: 'Doe', login: 'jdoe' }],
    }).as('getEtudiants');

    // Confirm it landed on etudiant page
    cy.visit('/etudiant');

    // Verify it's not on login page
    cy.url().should('not.include', '/login');

    cy.wait('@getEtudiants');
  });

  it('should create a new student', () => {
    cy.intercept('POST', '**/api/etudiants', { statusCode: 201 }).as(
      'createRequest',
    );

    cy.intercept('GET', '**/api/etudiants', {
      statusCode: 200,
      body: [
        { id: 1, firstName: 'John', lastName: 'Doe', login: 'jdoe' },
        { id: 2, firstName: 'Tony', lastName: 'Fred', login: 'tfred' },
      ],
    }).as('refreshList');

    cy.get('input[name="firstName"]').type('Tony');
    cy.get('input[name="lastName"]').type('Fred');
    cy.get('input[name="login"]').type('tfred');
    cy.get('button[type="submit"]').click();

    cy.wait('@createRequest');
    cy.wait('@refreshList');
    cy.get('table').should('contain', 'Tony');
  });

  it('should edit a student', () => {
    cy.intercept('PUT', '**/api/etudiants/1', { statusCode: 200 }).as(
      'updateRequest',
    );

    // Mock the refreshed data after update
    cy.intercept('GET', '**/api/etudiants', {
      statusCode: 200,
      body: [{ id: 1, firstName: 'Paul', lastName: 'Doe', login: 'jdoe' }],
    }).as('refreshList');

    cy.contains('tr', 'John').contains('button', 'Edit').click();

    cy.get('input[name="firstName"]').clear().type('Paul');

    cy.get('button[type="submit"]').click();

    cy.wait('@updateRequest');
    cy.wait('@refreshList');

    cy.get('table').should('contain', 'Paul');
    cy.get('table').should('not.contain', 'John');
  });

  it('should delete a student', () => {
    cy.intercept('DELETE', '**/api/etudiants/1', { statusCode: 200 }).as(
      'deleteRequest',
    );

    cy.contains('tr', 'John').contains('button', 'Delete').click();

    cy.wait('@deleteRequest');

    cy.get('table').should('not.contain', 'John');
  });
});
