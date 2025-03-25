package site.easy.to.build.crm.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import site.easy.to.build.crm.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    public List<Budget> findByCustomerCustomerId(int customerId);
     @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Budget b")
    BigDecimal getTotalAmount();
}
