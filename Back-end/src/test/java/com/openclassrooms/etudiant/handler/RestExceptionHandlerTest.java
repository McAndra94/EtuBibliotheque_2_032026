package com.openclassrooms.etudiant.handler;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.context.request.WebRequest;
import java.nio.file.AccessDeniedException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestExceptionHandlerTest {

    private final RestExceptionHandler handler = new RestExceptionHandler();
    private final WebRequest request = mock(WebRequest.class);

    @Test
    void handleConflict_shouldReturnBadRequest() {
        when(request.getDescription(false)).thenReturn("uri=/test");
        RuntimeException ex = new IllegalArgumentException("Invalid argument");

        ResponseEntity<Object> response = handler.handleConflict(ex, request);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void handleBadCredentials_shouldReturnUnauthorized() {
        when(request.getDescription(false)).thenReturn("uri=/test");
        BadCredentialsException ex = new BadCredentialsException("Bad credentials");

        ResponseEntity<Object> response = handler.handleBadCredentialsException(ex, request);

        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    void handleForbidden_shouldReturnForbidden() {
        when(request.getDescription(false)).thenReturn("uri=/test");
        AccessDeniedException ex = new AccessDeniedException("Access denied");

        ResponseEntity<Object> response = handler.handleForbiddenException(ex, request);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    void handleException_shouldReturnInternalServerError() {
        // Cette méthode n'utilise pas getErrorDetails, donc pas besoin de configurer le
        // mock
        RuntimeException ex = new RuntimeException("Generic error");

        ResponseEntity<Object> response = handler.handleException(ex, request);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }
}