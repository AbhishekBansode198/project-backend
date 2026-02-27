package onedeoleela.onedeoleela.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "vehicle_requisitions")
public class VehicleRequisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String requisitionNo;

    private String requisitionBy;

    private LocalDate requisitionDate;

    private LocalTime requisitionTime;

    private String projectName;

    private String locationFrom;

    private String locationTo;

    private String department;

    private String status;

    private Long createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRequisitionNo() { return requisitionNo; }
    public void setRequisitionNo(String requisitionNo) {
        this.requisitionNo = requisitionNo;
    }

    public String getRequisitionBy() { return requisitionBy; }
    public void setRequisitionBy(String requisitionBy) {
        this.requisitionBy = requisitionBy;
    }

    public LocalDate getRequisitionDate() { return requisitionDate; }
    public void setRequisitionDate(LocalDate requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public LocalTime getRequisitionTime() { return requisitionTime; }
    public void setRequisitionTime(LocalTime requisitionTime) {
        this.requisitionTime = requisitionTime;
    }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLocationFrom() { return locationFrom; }
    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() { return locationTo; }
    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}