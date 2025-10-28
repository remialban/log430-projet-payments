package ca.log430.payments;


import ca.log430.payments.domain.model.Approvisionnement;
import ca.log430.payments.ports.out.ApprovisionnementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    private final ApprovisionnementRepository approvisionnementRepository;

    public DataInitializer(ApprovisionnementRepository approvisionnementRepository) {
        this.approvisionnementRepository = approvisionnementRepository;
    }

    @Override
    public void run(String... args) {
        if (approvisionnementRepository.count() == 0) {
            Approvisionnement a1 = new Approvisionnement();
            a1.setUserId(1);
            a1.setAmount(500);
            a1.setCvv("123");
            a1.setExpirationDate("12/25");
            a1.setNumeroCarte("1234512345123456");


            approvisionnementRepository.save(a1);
            Approvisionnement a2 = new Approvisionnement();
            a2.setUserId(1);
            a2.setAmount(500);
            a2.setCvv("456");
            a2.setExpirationDate("12/42");
            a2.setNumeroCarte("9234512345123456");
            approvisionnementRepository.save(a2);
            System.out.println("✅ Données initiales insérées !");
        }
    }

}
