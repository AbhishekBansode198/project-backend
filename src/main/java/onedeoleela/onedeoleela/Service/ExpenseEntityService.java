package onedeoleela.onedeoleela.Service;


import onedeoleela.onedeoleela.Entity.ExpenseEntity;
import onedeoleela.onedeoleela.Repository.ExpenseEntityRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseEntityService {

    private final ExpenseEntityRepository expenseRepository;

    public ExpenseEntityService(ExpenseEntityRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public ExpenseEntity saveExpense(ExpenseEntity expense) {
        return expenseRepository.save(expense);
    }

    public List<ExpenseEntity> getExpensesByDriverAndVehicle(String eCode, String vehicleNumber) {
        return expenseRepository.findByDriverECodeAndVehicleNumberOrderByDateDesc(eCode, vehicleNumber);
    }

    public List<ExpenseEntity> getAllExpensesByDriver(String eCode) {
        return expenseRepository.findByDriverECodeOrderByDateDesc(eCode);
    }
}