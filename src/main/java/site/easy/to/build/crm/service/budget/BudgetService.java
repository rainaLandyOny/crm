package site.easy.to.build.crm.service.budget;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.easy.to.build.crm.entity.Budget;
import site.easy.to.build.crm.entity.RateConfig;
import site.easy.to.build.crm.repository.BudgetRepository;
import site.easy.to.build.crm.service.expense.ExpenseService;
import site.easy.to.build.crm.service.rate.RateConfigService;


@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    @Autowired
    private ExpenseService expenseService;
    private final RateConfigService rateConfigService;

    public BudgetService(BudgetRepository budgetRepository, RateConfigService rateConfigService,ExpenseService expenseService) {
        this.budgetRepository = budgetRepository;
     
        this.rateConfigService = rateConfigService;

        this.expenseService = expenseService;
    }

    public Budget save(Budget budget) {
        budgetRepository.save(budget);
        return budget;
    }

    public List<Budget> getCustomerBudgets(int customerId) {
        return budgetRepository.findByCustomerCustomerId(customerId);
    }
    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

       // Calculer la somme des montants de tous les budgets
    // public BigDecimal sumAllBudgetsAmount() {
    //     List<Budget> budgets = budgetRepository.findAll();
    //     return budgets.stream()
    //                   .map(Budget::getAmount)  // Récupère l'attribut `amount` (BigDecimal)
    //                   .filter(amount -> amount != null) // Évite les valeurs nulles
    //                   .reduce(BigDecimal.ZERO, BigDecimal::add); // Additionne tous les montants
    // }
    // public BigDecimal sumAllBudgetsAmount() {
    //     return budgetRepository.getTotalAmount();
    // }
    
    // Calculer la somme des montants pour un client spécifique
    public BigDecimal sumBudgetAmountByCustomer(int customerId) {
        List<Budget> customerBudgets = budgetRepository.findByCustomerCustomerId(customerId);
        return customerBudgets.stream()
                              .map(Budget::getAmount)
                              .filter(amount -> amount != null)
                              .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalCustomerBudgets(int customerId) {
        List<Budget> budgets = getCustomerBudgets(customerId);
        BigDecimal totalBudget = BigDecimal.ZERO;
        for (Budget budget : budgets) {
            totalBudget = totalBudget.add(budget.getAmount());
        }
        return totalBudget;
    }

    public BigDecimal getRealBudget(int customerId) {
        return getTotalCustomerBudgets(customerId).subtract(expenseService.getCustomerDepense(customerId));
    }

    public boolean isBudgetTargetReached(int customerId, BigDecimal add) {
        return (expenseService.getCustomerDepense(customerId).add(add))
                .compareTo(getTotalCustomerBudgets(customerId)) > 0;
    }

    public boolean isRateAlertReached(int customerId, BigDecimal add) {
        Optional<RateConfig> rateConfig = rateConfigService.findLatest();
        BigDecimal tauxAlert = rateConfig.get().getRate();

        BigDecimal totalBudgets = getTotalCustomerBudgets(customerId);
        BigDecimal totalExpenses = expenseService.getCustomerDepense(customerId).add(add);

        if (totalBudgets.compareTo(BigDecimal.ZERO) == 0) {
            return false;
        }

        BigDecimal pourcentageDepense = totalExpenses
                .multiply(new BigDecimal("100"))
                .divide(totalBudgets, 2, RoundingMode.HALF_UP);
        System.out.println("budget: " + totalBudgets + "");
        System.out.println("expense: " + totalExpenses + "");
        System.out.println("tauxAlert: " + tauxAlert + "");

        return pourcentageDepense.compareTo(tauxAlert) >= 0;
    }
    
}
