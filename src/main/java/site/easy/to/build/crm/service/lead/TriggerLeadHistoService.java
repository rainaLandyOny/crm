package site.easy.to.build.crm.service.lead;
import site.easy.to.build.crm.entity.TriggerLeadHisto;

import java.time.LocalDateTime;
import java.util.List;

public interface TriggerLeadHistoService {
    public TriggerLeadHisto save(TriggerLeadHisto triggerLeadHisto);

    List<TriggerLeadHisto> getBetweenDate(LocalDateTime date1, LocalDateTime date2);
}