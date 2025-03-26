package site.easy.to.build.crm.service.lead;
import site.easy.to.build.crm.entity.TriggerLeadHisto;

import java.time.LocalDateTime;
import java.util.List;

public interface TriggerLeadHistoService {
    public TriggerLeadHisto save(TriggerLeadHisto triggerLeadHisto);

    
    public void softDelete(Integer id);
    List<TriggerLeadHisto> getTriggerLeadHistoBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    public List<TriggerLeadHisto> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    TriggerLeadHisto getById(Integer id);


    List<TriggerLeadHisto> getAll();


    TriggerLeadHisto update(Integer id, TriggerLeadHisto triggerLeadHisto);


    void delete(Integer id);






    
}