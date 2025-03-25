package site.easy.to.build.crm.service.lead;

import site.easy.to.build.crm.entity.LeadExpense;
import java.util.List;

public interface LeadExpenseService {
    LeadExpense save(LeadExpense leadExpense);
    
    LeadExpense findLatestByTriggerLeadHistoId(Integer triggerLeadHistoId);

    List<LeadExpense> findAll(); // Ajout de la méthode pour récupérer toutes les LeadExpense
}
