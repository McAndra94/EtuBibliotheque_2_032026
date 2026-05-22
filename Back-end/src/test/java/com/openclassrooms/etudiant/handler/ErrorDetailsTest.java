package com.openclassrooms.etudiant.handler;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class ErrorDetailsTest {

    @Test
    void testErrorDetailsCoverage() {
        LocalDateTime now = LocalDateTime.now();

        ErrorDetails details1 = new ErrorDetails();
        details1.setTimestamp(now);
        details1.setMessage("test");
        details1.setDetails("info");

        ErrorDetails details2 = new ErrorDetails(now, "test", "info");

        assertThat(details1.getTimestamp()).isEqualTo(now);
        assertThat(details1.getMessage()).isEqualTo("test");
        assertThat(details1.getDetails()).isEqualTo("info");

        assertThat(details1).isEqualTo(details2);
        assertThat(details1.hashCode()).isEqualTo(details2.hashCode());
        assertThat(details1).isNotEqualTo(null);

        assertThat(details1.toString()).contains("test", "info");
    }
}