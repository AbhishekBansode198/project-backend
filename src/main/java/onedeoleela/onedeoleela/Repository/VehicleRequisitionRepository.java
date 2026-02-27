package onedeoleela.onedeoleela.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import onedeoleela.onedeoleela.Entity.VehicleRequisition;

public interface VehicleRequisitionRepository
        extends JpaRepository<VehicleRequisition, Long> {

    VehicleRequisition findTopByOrderByIdDesc();
}