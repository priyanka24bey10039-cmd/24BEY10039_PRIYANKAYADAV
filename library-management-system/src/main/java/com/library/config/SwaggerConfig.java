package com.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI Configuration
 * Access Swagger UI at: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI libraryManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Management System API")
                        .description("""
                                ## Library Management System REST API
                                
                                A comprehensive backend API for managing library resources.
                                
                                ### Features:
                                - **Author Management** - CRUD operations for authors
                                - **Category Management** - CRUD operations for book categories
                                - **Book Management** - CRUD + search by title, author, category
                                - **Member Management** - CRUD operations for library members
                                - **Library Card Management** - Issue and manage library cards
                                - **Issue Records** - Track book issuance and returns
                                
                                ### Business Rules:
                                - Books can only be issued if `availableQuantity > 0`
                                - Returning a book increments `availableQuantity`
                                - Each member can have only one active library card
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Library Management Team")
                                .email("admin@library.com")
                                .url("https://github.com/library-management"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server")
                ));
    }
}
