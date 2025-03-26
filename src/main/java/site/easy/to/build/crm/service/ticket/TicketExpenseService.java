package site.easy.to.build.crm.service.ticket;


import site.easy.to.build.crm.entity.TicketExpense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketExpenseService {
    TicketExpense save(TicketExpense ticketExpense);

    TicketExpense getLatestExpenseForTicketHisto(int ticketHistoId);
    BigDecimal getTotalExpensesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    // Nouvelle méthode pour récupérer toutes les dépenses des tickets
    List<TicketExpense> getAllTicketExpenses();
}
