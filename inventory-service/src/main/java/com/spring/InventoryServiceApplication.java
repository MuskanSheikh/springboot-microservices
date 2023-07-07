package com.spring;

import com.spring.entity.Inventory;
import com.spring.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository){
        return args -> {
            Inventory inv1 = new Inventory();
            inv1.setSkuCode("samsung_M31");
            inv1.setQuantity(1L);
            Inventory inv2 = new Inventory();
            inv2.setSkuCode("Oppo_reno_8T");
            inv2.setQuantity(0L);
            inventoryRepository.save(inv1);
            inventoryRepository.save(inv2);
        };
    }
}