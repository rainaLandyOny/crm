package site.easy.to.build.crm.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import site.easy.to.build.crm.entity.Budget;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.LeadExpense;
import site.easy.to.build.crm.entity.RateConfig;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.TicketExpense;
import site.easy.to.build.crm.entity.TicketHisto;
import site.easy.to.build.crm.entity.TriggerLeadHisto;
import site.easy.to.build.crm.service.budget.BudgetService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadExpenseService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.rate.RateConfigService;
import site.easy.to.build.crm.service.ticket.TicketExpenseService;
import site.easy.to.build.crm.service.ticket.TicketHistoService;
import site.easy.to.build.crm.service.ticket.TicketService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rest")
public class ApiRestController {

    private final CustomerService customerService;
    private final LeadService leadService;
    private final TicketService ticketService;
    private final BudgetService budgetService;
    private final LeadExpenseService leadExpenseService;
    private final TicketExpenseService ticketExpenseService; 
    private final RateConfigService rateConfigService;
    private final TicketHistoService ticketHistoService;

    public ApiRestController(CustomerService customerService, LeadService leadService, TicketService ticketService, BudgetService budgetService, LeadExpenseService leadExpenseService , TicketExpenseService ticketExpenseService, RateConfigService rateConfigService,TicketHistoService ticketHistoService) {
        this.customerService = customerService;
        this.leadService = leadService;
        this.ticketService = ticketService;
        this.budgetService = budgetService;
        this.leadExpenseService = leadExpenseService;
        this.ticketExpenseService = ticketExpenseService;
        this.rateConfigService = rateConfigService;
        this.ticketHistoService = ticketHistoService;
    }

    // Gestion des clients
    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    // Gestion des leads
    @GetMapping("/leads")
    public List<Lead> getAllLeads() {
        return leadService.findAll();
    }

    // Gestion des tickets
    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        return ticketService.findAll();
    }

    // Gestion des budgets (Liste complète)
    @GetMapping("/budgets")
    public List<Budget> getAllBudgets() {
        return budgetService.findAll();
    }

    // // Récupération du montant total des budgets
    // @GetMapping("/budgets")
    // public ResponseEntity<BigDecimal> getTotalBudgetsAmount() {
    //     BigDecimal totalAmount = budgetService.sumAllBudgetsAmount();
    //     return ResponseEntity.ok(totalAmount != null ? totalAmount : BigDecimal.ZERO);
    // }
       // Gestion des LeadExpense (Liste complète)
    @GetMapping("/lead-expenses")
    public List<LeadExpense> getAllLeadExpenses() {
        return leadExpenseService.findAll();
    }
       // Gestion des TicketExpense (Liste complète)
    @GetMapping("/ticket-expenses")
    public List<TicketExpense> getAllTicketExpenses() {
        return ticketExpenseService.getAllTicketExpenses();
    }

    // Endpoint pour récupérer toutes les configurations de taux
    @GetMapping
    public ResponseEntity<List<RateConfig>> getAllRateConfigs(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        List<RateConfig> rateConfigs = rateConfigService.findBetweenDates(startDate, endDate);
        return new ResponseEntity<>(rateConfigs, HttpStatus.OK);
    }





     @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int customerId) {
        Customer customer = customerService.findByCustomerId(customerId);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Récupérer tous les clients d'un utilisateur spécifique
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Customer>> getCustomersByUserId(@PathVariable int userId) {
        List<Customer> customers = customerService.findByUserId(userId);
        return ResponseEntity.ok(customers);
    }

    // Récupérer un client par son email
    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.findByEmail(email);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // // Créer un nouveau client
    // @PostMapping
    // public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    //     Customer savedCustomer = customerService.save(customer);
    //     return ResponseEntity.ok(savedCustomer);
    // }

    // Supprimer un client
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int customerId) {
        Customer customer = customerService.findByCustomerId(customerId);
        if (customer != null) {
            customerService.delete(customer);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Récupérer les clients récents pour un utilisateur
    @GetMapping("/recent/{userId}")
    public ResponseEntity<List<Customer>> getRecentCustomers(
            @PathVariable int userId,
            @RequestParam(defaultValue = "5") int limit) {
        List<Customer> recentCustomers = customerService.getRecentCustomers(userId, limit);
        return ResponseEntity.ok(recentCustomers);
    }

    // Compter le nombre de clients pour un utilisateur
    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> countCustomersByUserId(@PathVariable int userId) {
        long count = customerService.countByUserId(userId);
        return ResponseEntity.ok(count);
    }


    // Récupérer un lead par son ID
    @GetMapping("/lead/{id}")
    public ResponseEntity<Lead> getLeadById(@PathVariable int id) {
        Lead lead = leadService.findByLeadId(id);
        if (lead != null) {
            return ResponseEntity.ok(lead);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Récupérer les leads assignés à un utilisateur
    @GetMapping("/assigned/{userId}")
    public ResponseEntity<List<Lead>> getAssignedLeads(@PathVariable int userId) {
        List<Lead> leads = leadService.findAssignedLeads(userId);
        return ResponseEntity.ok(leads);
    }

    // Récupérer les leads créés par un utilisateur
    @GetMapping("/created/{userId}")
    public ResponseEntity<List<Lead>> getCreatedLeads(@PathVariable int userId) {
        List<Lead> leads = leadService.findCreatedLeads(userId);
        return ResponseEntity.ok(leads);
    }

    // Récupérer un lead par son ID de réunion
    @GetMapping("/meeting/{meetingId}")
    public ResponseEntity<Lead> getLeadByMeetingId(@PathVariable String meetingId) {
        Lead lead = leadService.findByMeetingId(meetingId);
        if (lead != null) {
            return ResponseEntity.ok(lead);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // // Créer un nouveau lead
    // @PostMapping
    // public ResponseEntity<Lead> createLead(@RequestBody Lead lead) {
    //     Lead savedLead = leadService.save(lead);
    //     return ResponseEntity.ok(savedLead);
    // }

    // Supprimer un lead
    @DeleteMapping("lead/supr/{id}")
    public ResponseEntity<Void> deleteLead(@PathVariable int id) {
        Lead lead = leadService.findByLeadId(id);
        if (lead != null) {
            leadService.delete(lead);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Récupérer les leads récents pour un manager
    @GetMapping("/recent/manager/{managerId}")
    public ResponseEntity<List<Lead>> getRecentLeadsForManager(
            @PathVariable int managerId,
            @RequestParam(defaultValue = "5") int limit) {
        List<Lead> recentLeads = leadService.getRecentLeads(managerId, limit);
        return ResponseEntity.ok(recentLeads);
    }

    // Récupérer les leads d'un client spécifique
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Lead>> getLeadsByCustomerId(@PathVariable int customerId) {
        List<Lead> leads = leadService.getCustomerLeads(customerId);
        return ResponseEntity.ok(leads);
    }

    // Compter le nombre de leads assignés à un employé
    @GetMapping("/count/employee/{employeeId}")
    public ResponseEntity<Long> countLeadsByEmployeeId(@PathVariable int employeeId) {
        long count = leadService.countByEmployeeId(employeeId);
        return ResponseEntity.ok(count);
    }

    // Compter le nombre de leads assignés à un manager
    @GetMapping("/count/manager/{managerId}")
    public ResponseEntity<Long> countLeadsByManagerId(@PathVariable int managerId) {
        long count = leadService.countByManagerId(managerId);
        return ResponseEntity.ok(count);
    }

    // Compter le nombre de leads pour un client
    @GetMapping("/count/customer/{customerId}")
    public ResponseEntity<Long> countLeadsByCustomerId(@PathVariable int customerId) {
        long count = leadService.countByCustomerId(customerId);
        return ResponseEntity.ok(count);
    }

    // Récupérer les leads récents pour un employé
    @GetMapping("/recent/employee/{employeeId}")
    public ResponseEntity<List<Lead>> getRecentLeadsByEmployee(
            @PathVariable int employeeId,
            @RequestParam(defaultValue = "5") int limit) {
        List<Lead> recentLeads = leadService.getRecentLeadsByEmployee(employeeId, limit);
        return ResponseEntity.ok(recentLeads);
    }

    // Récupérer les leads récents pour un client
    @GetMapping("/recent/customer/{customerId}")
    public ResponseEntity<List<Lead>> getRecentLeadsByCustomer(
            @PathVariable int customerId,
            @RequestParam(defaultValue = "5") int limit) {
        List<Lead> recentLeads = leadService.getRecentCustomerLeads(customerId, limit);
        return ResponseEntity.ok(recentLeads);
    }

    // Supprimer tous les leads d'un client
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteLeadsByCustomer(@PathVariable int customerId) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        leadService.deleteAllByCustomer(customer);
        return ResponseEntity.noContent().build();
    }



    // Récupérer une dépense par son ID
    @GetMapping("/ticket/{id}")
    public ResponseEntity<TicketExpense> getById(@PathVariable int id) {
        // Récupération de la dépense à partir du service
        TicketExpense ticketExpense = ticketExpenseService.getLatestExpenseForTicketHisto(id);

        if (ticketExpense != null) {
            // Si une dépense est trouvée, la retourner avec un statut HTTP 200
            return ResponseEntity.ok(ticketExpense);
        } else {
            // Si aucune dépense n'est trouvée, retourner une dépense avec des valeurs par défaut
            TicketExpense defaultTicketExpense = new TicketExpense();
            defaultTicketExpense.setId(0);
            defaultTicketExpense.setAmount(BigDecimal.ZERO);  // Utilisation de BigDecimal.ZERO pour un montant par défaut
            defaultTicketExpense.setCreatedAt(LocalDateTime.MIN);  // Valeur par défaut pour la date de création

            return ResponseEntity.ok(defaultTicketExpense);
        }
    }





    // Mettre à jour une dépense
    @PutMapping("/modif/{id}")
    public ResponseEntity<TicketExpense> update(@PathVariable int id, @Valid @RequestBody TicketExpense updatedTicketExpense) {
        TicketExpense existingTicketExpense = ticketExpenseService.getLatestExpenseForTicketHisto(id);

        if (existingTicketExpense == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        existingTicketExpense.setAmount(updatedTicketExpense.getAmount());
        existingTicketExpense.setCreatedAt(updatedTicketExpense.getCreatedAt());
        existingTicketExpense.setTicketHisto(updatedTicketExpense.getTicketHisto());

        TicketExpense savedTicketExpense = ticketExpenseService.save(existingTicketExpense);
        return ResponseEntity.ok(savedTicketExpense); // 200 OK
    }

     @GetMapping("ticketHisto/{id}")
    public ResponseEntity<TicketHisto> getTicketById(@PathVariable int id) {
        TicketHisto ticket = ticketHistoService.findByTicketHistoId(id);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-ticket/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable("id") int id) {
        try {
            Ticket ticket = ticketService.findByTicketId(id);
            TicketHisto ticketHisto = ticketHistoService.findByTicketHistoId(id);

            if (ticket == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket non trouvé avec l'ID : " + id);
            }

            ticketService.delete(ticket);
            ticketHisto.setDeleteAt(LocalDateTime.now());
            ticketHistoService.save(ticketHisto);

            return ResponseEntity.ok("Ticket supprimé avec succès.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression du ticket : " + ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketHisto>> getAllHistorique(
            @RequestParam(required = false) LocalDateTime date1,
            @RequestParam(required = false) LocalDateTime date2
    ) {
        List<TicketHisto> ticketHistos = ticketHistoService.getBetweenDate(date1, date2);
        return ResponseEntity.ok(ticketHistos);
    }
    


    //  @GetMapping("/{id}")
    // public ResponseEntity<LeadExpense> getExpenseById(@PathVariable("id") int id) {
    //     Optional<LeadExpense> expense = Optional.ofNullable(leadExpenseService.findLatestByTriggerLeadHistoId(id));
    //     return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    // }

    // @GetMapping("/leadExpense/{id}")
    // public ResponseEntity<LeadExpense> getExpenseByIdLead(@PathVariable("id") int id) {
    //     Optional<LeadExpense> expense = Optional.ofNullable(leadExpenseService.findById(id));
    //     return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    // }

    // @PutMapping("/update")
    // public ResponseEntity<LeadExpense> updateLeadExpense(@RequestBody LeadExpenseUpdateDto updateDto) {
    //     try {
    //         // 1. Valider les données reçues
    //         if (updateDto == null) {
    //             return ResponseEntity.badRequest().build();
    //         }
    //         TriggerLeadHisto leadhisto=new TriggerLeadHisto();
    //         leadhisto.setId(updateDto.getLeadId());
    //         // 3. Mettre à jour les champs
    //         LeadExpense expenseToUpdate = new LeadExpense();
    //         expenseToUpdate.setAmount(updateDto.getAmount());
    //         expenseToUpdate.setCreatedAt(updateDto.getCreatedAt());
    //         expenseToUpdate.setTriggerLeadHisto(leadhisto);
    //         LeadExpense updatedExpense = leadExpenseService.save(expenseToUpdate);

    //         // 5. Retourner la réponse
    //         return ResponseEntity.ok(updatedExpense);
            
    //     } catch (Exception e) {
    //         return ResponseEntity.internalServerError().build();
    //     }
    // }
}

