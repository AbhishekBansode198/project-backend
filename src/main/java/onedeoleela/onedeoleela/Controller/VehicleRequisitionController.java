package onedeoleela.onedeoleela.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import onedeoleela.onedeoleela.Entity.VehicleRequisition;
import onedeoleela.onedeoleela.Service.VehicleRequisitionService;
import onedeoleela.onedeoleela.Repository.VehicleRequisitionRepository;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-requisition")
@CrossOrigin
public class VehicleRequisitionController {

    @Autowired
    private VehicleRequisitionService service;

    @Autowired
    private VehicleRequisitionRepository repository;

    // ================= CREATE =================
    @PostMapping
    public VehicleRequisition create(
            @RequestBody VehicleRequisition req,
            @RequestHeader("department") String department) {

        // ✅ ACCESS CHECK
        if (!canCreate(department)) {
            throw new RuntimeException("Access Denied");
        }

        // ✅ VERY IMPORTANT FIX
        req.setDepartment(department);
        req.setStatus("Pending");

        return service.createRequisition(req);
    }

    // ================= GET ALL =================
    @GetMapping
    public List<VehicleRequisition> getAll() {
        return repository.findAll();
    }

    // ================= PERMISSION =================
    private boolean canCreate(String department) {

        return department.equals("Production") ||
                department.equals("Purchase") ||
                department.equals("Powder Coating") ||
                department.equals("Coordinator") ||
                department.equals("Admin");
    }
}