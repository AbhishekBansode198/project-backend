package onedeoleela.onedeoleela.Entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "driver_expenses")
@Data
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // These link the expense to the context (Sent from Frontend)
    @Column(nullable = false)
    private String vehicleNumber;

    @Column(nullable = false)
    private String driverECode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseType type;

    @Column(nullable = false)
    private Double amount;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;
}