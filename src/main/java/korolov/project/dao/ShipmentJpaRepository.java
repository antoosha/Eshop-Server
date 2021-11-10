package korolov.project.dao;

import korolov.project.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentJpaRepository extends JpaRepository<Shipment, Long> {
}
