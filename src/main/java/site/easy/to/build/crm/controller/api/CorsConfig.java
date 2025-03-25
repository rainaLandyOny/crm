package site.easy.to.build.crm.controller.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Applique CORS à toutes les routes sous /api
                .allowedOrigins(
                    "https://localhost:7220", // Autorise les requêtes depuis cette URL
                    "http://localhost:5048"   // Autorise les requêtes depuis cette URL
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes HTTP autorisées
                .allowedHeaders("*") // En-têtes autorisés
                .allowCredentials(true); // Autorise les cookies et les en-têtes d'authentification
    }
}
