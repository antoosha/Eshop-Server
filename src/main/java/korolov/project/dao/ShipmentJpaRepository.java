package korolov.project.dao;

import korolov.project.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentJpaRepository extends JpaRepository<Shipment, Long> {
    /**
     * Find shipments with this client email.
     *
     * @param clientEmail
     * @return
     */
    List<Shipment> findAllByOrderClientEmail(String clientEmail);
}
