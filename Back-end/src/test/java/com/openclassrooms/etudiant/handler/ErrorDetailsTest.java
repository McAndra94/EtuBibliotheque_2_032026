package com.openclassrooms.etudiant.handler;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class ErrorDetailsTest {

    @Test
    void shouldCreateErrorDetails() {
        LocalDateTime now = LocalDateTime.now();
        ErrorDetails details = new ErrorDetails(now, "Msg", "Desc");

        assertThat(details.getMessage()).isEqualTo("Msg");
        assertThat(details.getDetails()).isEqualTo("Desc");
        assertThat(details.getTimestamp()).isEqualTo(now);
    }
}