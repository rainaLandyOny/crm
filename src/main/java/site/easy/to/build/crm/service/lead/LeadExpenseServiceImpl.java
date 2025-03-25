package site.easy.to.build.crm.service.lead;

import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.LeadExpense;
import site.easy.to.build.crm.repository.LeadExpenseRepository;

import java.util.List;

@Service
public class LeadExpenseServiceImpl implements LeadExpenseService {
    private final LeadExpenseRepository leadExpenseRepository;

    public LeadExpenseServiceImpl(LeadExpenseRepository leadExpenseRepository) {
        this.leadExpenseRepository = leadExpenseRepository;
    }

    @Override
    public LeadExpense save(LeadExpense leadExpense) {
        return leadExpenseRepository.save(leadExpense);
    }
    
    @Override
    public LeadExpense findLatestByTriggerLeadHistoId(Integer triggerLeadHistoId) {
        return leadExpenseRepository.findLatestByTriggerLeadHistoId(triggerLeadHistoId);
    }

    @Override
    public List<LeadExpense> findAll() {
        return leadExpenseRepository.findAll(); // Implémentation de la nouvelle méthode
    }
}
