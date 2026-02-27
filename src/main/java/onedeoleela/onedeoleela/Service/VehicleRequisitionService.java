package onedeoleela.onedeoleela.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onedeoleela.onedeoleela.Entity.VehicleRequisition;
import onedeoleela.onedeoleela.Repository.VehicleRequisitionRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VehicleRequisitionService {

    @Autowired
    private VehicleRequisitionRepository repository;

    // ================= CREATE =================
    public VehicleRequisition createRequisition(VehicleRequisition req) {

        // Auto Requisition Number
        req.setRequisitionNo(generateReqNo());

        // ✅ USE USER DATE IF PROVIDED
        if (req.getRequisitionDate() == null) {
            req.setRequisitionDate(LocalDate.now());
        }

        // ✅ USE USER TIME IF PROVIDED
        if (req.getRequisitionTime() == null) {
            req.setRequisitionTime(LocalTime.now());
        }

        // Default Status
        if (req.getStatus() == null) {
            req.setStatus("Pending");
        }

        return repository.save(req);
    }

    // ================= AUTO REQ NO =================
    private String generateReqNo() {

        VehicleRequisition last =
                repository.findTopByOrderByIdDesc();

        int nextNumber = 1;

        if (last != null && last.getRequisitionNo() != null) {

            String[] parts = last.getRequisitionNo().split("-");

            nextNumber = Integer.parseInt(parts[2]) + 1;
        }

        int year = LocalDate.now().getYear();

        return String.format("VR-%d-%04d", year, nextNumber);
    }
}