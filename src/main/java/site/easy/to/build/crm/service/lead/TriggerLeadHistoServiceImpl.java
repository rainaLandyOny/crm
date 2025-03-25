package site.easy.to.build.crm.service.lead;

import org.springframework.stereotype.Service;

import site.easy.to.build.crm.entity.TriggerLeadHisto;
import site.easy.to.build.crm.repository.TriggerLeadHistoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TriggerLeadHistoServiceImpl implements TriggerLeadHistoService{
    private final TriggerLeadHistoRepository triggerLeadHistoRepository;

    public TriggerLeadHistoServiceImpl(TriggerLeadHistoRepository triggerLeadHistoRepository) {
        this.triggerLeadHistoRepository = triggerLeadHistoRepository;
    }
    @Override
    public TriggerLeadHisto save(TriggerLeadHisto triggerLeadHisto){
        return triggerLeadHistoRepository.save(triggerLeadHisto);
    }

    @Override
    public List<TriggerLeadHisto> getBetweenDate(LocalDateTime date1, LocalDateTime date2) {
        return triggerLeadHistoRepository.getByDate(date1, date2);
    }
}
