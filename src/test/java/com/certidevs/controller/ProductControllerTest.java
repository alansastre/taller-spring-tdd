package com.certidevs.controller;

import com.certidevs.entity.Product;
import com.certidevs.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Testing de integración
@SpringBootTest
@AutoConfigureMockMvc // herramienta para lanzar peticiones HTTP y probar la aplicación
@Transactional
public class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;

    // CRUD - Create, Read, Update, Delete

    // Ver todos los productos
    @Test
    void findAll() throws Exception {

        productRepository.saveAll(List.of(
                new Product(1L, "ordenador", 500d),
                new Product(2L, "smartphone", 450d),
                new Product(3L, "botella", 450d)
        ));

        // http://localhost:8080/api/products
         mockMvc.perform(get("/api/products"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$", hasSize(3)))
                 .andExpect(jsonPath("$[0].title").value("ordenador")); // 200
    }

    @Test
    void save() throws Exception {

        // Fase 1: dato para la prueba:

        // Fase 2: ejecutar metodo o funcionalidad a testear
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "title": "producto test",
                    "price": 40.3
                }
                """)
        ).andExpect(status().isCreated()) // 201
        .andExpect(jsonPath("$.title").value("producto test"))
        .andExpect(jsonPath("$.price").value(40.3));

        // Fase 3: comprobaciones, asserts, expect
        Optional<Product> productOpt = productRepository.findByTitle("producto test");
        productOpt.ifPresent(product -> assertNotNull(product.getId()));
    }


    // Ver un producto

    // Crear un nuevo producto

    // Actualizar un producto

    // Borrar un producto
}
