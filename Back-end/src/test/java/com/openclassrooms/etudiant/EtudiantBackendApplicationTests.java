package com.openclassrooms.etudiant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EtudiantBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainStartsApplication() {
        EtudiantBackendApplication.main(new String[] {});
    }
}