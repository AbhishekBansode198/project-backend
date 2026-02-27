package onedeoleela.onedeoleela.Controller;


import onedeoleela.onedeoleela.Entity.ExpenseEntity;
import onedeoleela.onedeoleela.Service.ExpenseEntityService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*") // Allows React frontend to connect
public class ExpenseEntityController  {


    private final ExpenseEntityService expenseService;

    public ExpenseEntityController(ExpenseEntityService expenseService) {
        this.expenseService = expenseService;
    }

    // POST: Log a new expense
    @PostMapping("/add")
    public ResponseEntity<ExpenseEntity> addExpense(@RequestBody ExpenseEntity expense) {
        if (expense.getVehicleNumber() == null || expense.getDriverECode() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(expenseService.saveExpense(expense));
    }

    // GET: View expenses for a specific vehicle by a specific driver
    @GetMapping("/view/{vehicleNumber}")
    public ResponseEntity<List<ExpenseEntity>> viewExpenses(
            @PathVariable String vehicleNumber,
            @RequestParam String eCode) {

        List<ExpenseEntity> history = expenseService.getExpensesByDriverAndVehicle(eCode, vehicleNumber);
        return ResponseEntity.ok(history);
    }

    // GET: View all expenses for a specific driver (for dashboard stats)
    @GetMapping("/driver/{eCode}")
    public ResponseEntity<List<ExpenseEntity>> getDriverHistory(@PathVariable String eCode) {
        return ResponseEntity.ok(expenseService.getAllExpensesByDriver(eCode));
    }
}