package site.easy.to.build.crm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.easy.to.build.crm.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TriggerLeadHistoRepository extends JpaRepository<TriggerLeadHisto, Long> {

    @Query("SELECT t FROM TriggerLeadHisto t " +
            "WHERE (:date1 IS NULL OR t.createdAt >= :date1) " +
            "AND (:date2 IS NULL OR t.createdAt <= :date2)")
    List<TriggerLeadHisto> getByDate(@Param("date1") LocalDateTime date1, @Param("date2") LocalDateTime date2);
    List<TriggerLeadHisto> findByCustomerCustomerId(int idCustomer);
}