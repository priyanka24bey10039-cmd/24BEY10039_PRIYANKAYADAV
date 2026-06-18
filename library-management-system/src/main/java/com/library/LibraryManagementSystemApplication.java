package com.library;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Library Management System - Main Application Entry Point
 *
 * <p>A production-ready REST API for managing library resources including
 * authors, books, categories, members, library cards, and issue records.</p>
 *
 * <p>Technologies Used:</p>
 * <ul>
 *   <li>Spring Boot 3.x</li>
 *   <li>Spring Data MongoDB</li>
 *   <li>Swagger/OpenAPI 3.0</li>
 *   <li>Lombok</li>
 *   <li>Maven</li>
 * </ul>
 *
 * <p>Swagger UI: http://localhost:8080/swagger-ui.html</p>
 * <p>API Docs:   http://localhost:8080/api-docs</p>
 */
@Slf4j
@SpringBootApplication
@EnableMongoAuditing
public class LibraryManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
        log.info("==========================================================");
        log.info("  Library Management System started successfully!");
        log.info("  Swagger UI : http://localhost:8080/swagger-ui.html");
        log.info("  API Docs   : http://localhost:8080/api-docs");
        log.info("==========================================================");
    }
}
