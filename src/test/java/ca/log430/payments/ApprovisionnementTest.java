// java
package ca.log430.payments;

import ca.log430.payments.domain.model.Approvisionnement;
import ca.log430.payments.ports.out.ApprovisionnementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApprovisionnementTest {

    @Autowired
    private ApprovisionnementRepository repository;

    @Test
    public void addApprovisionnementTest() {
        // nettoyer le repository avant test
        repository.deleteAll();

        // créer 5 approvisionnements avec valeurs distinctes
        List<Approvisionnement> approvisionnements = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> {
                    Approvisionnement a = new Approvisionnement();
                    a.setAmount(100 + i);
                    a.setCvv(String.format("%03d", 100 + i));
                    a.setUserId(i);
                    return a;
                })
                .collect(Collectors.toList());

        // persister
        //Iterable<Approvisionnement> savedIterable = repository.saveAll(approvisionnements);
        for (Approvisionnement a : approvisionnements) {
            repository.save(a);
        }
        Iterable<Approvisionnement> savedIterable = repository.findAll();
        for (Approvisionnement a : savedIterable) {
            assertNotNull(a.getId(), "L'ID de l'approvisionnement sauvegardé ne doit pas être null");
        }
        List<Approvisionnement> saved = new ArrayList<>();
        savedIterable.forEach(saved::add);

        // assertions basiques
        assertEquals(5, saved.size(), "Doit sauvegarder 5 approvisionnements");
        assertEquals(5, repository.count(), "Le repository doit contenir 5 enregistrements");
        for (int i = 0; i < saved.size(); i++) {
            Approvisionnement a = saved.get(i);
            assertNotNull(a, "Approvisionnement ne doit pas être null");
            assertEquals(i + 1, a.getUserId(), "userId attendu");
            assertTrue(a.getAmount() > 0, "amount doit être positif");
            assertNotNull(a.getCvv(), "cvv ne doit pas être null");
            assertEquals(3, a.getCvv().length(), "cvv doit avoir 3 caractères");
        }
    }
}
