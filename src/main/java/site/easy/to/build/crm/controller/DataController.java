package site.easy.to.build.crm.controller;
import site.easy.to.build.crm.service.database.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/database")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/data-setting")
    public String showDataSettingsPage(Model model) {
        // Ici, tu peux ajouter des attributs à ton modèle si nécessaire
        return "/donnee/data"; // Retourne la vue data.html
    }

    // Endpoint pour supprimer toutes les données
    // @PostMapping("/delete-all")
    // public ResponseEntity<String> deleteAllData() {
    //     try {
    //         dataService.deleteAllData();
    //         return "redirect:/";
    //     } catch (Exception e) {
    //         return ResponseEntity.status(500).body("Erreur lors de la suppression des données.");
    //     }
    // }
    @PostMapping("/delete-all")
public String deleteAllData(RedirectAttributes redirectAttributes) {
    try {
        dataService.deleteAllData();
        redirectAttributes.addFlashAttribute("message", "Données supprimées avec succès !");
        return "redirect:/";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression des données.");
        return "redirect:/database/data-setting";
    }
}

}
