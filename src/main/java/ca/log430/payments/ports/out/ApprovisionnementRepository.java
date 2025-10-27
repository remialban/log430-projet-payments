package ca.log430.payments.ports.out;

import ca.log430.payments.domain.model.Approvisionnement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Integer> {
    public Approvisionnement save(Approvisionnement ordre);

    public List<Approvisionnement> findOrdreByUserId(Integer userId);


}
