package com.dio.barber_shop_spring.profiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.dio.barber_shop_spring.services.DBService;

@Configuration
@Profile("dev")
public class DevDBInitializer {

    private final DBService dbService;

    public DevDBInitializer(DBService dbService) {
        this.dbService = dbService;
    }

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Bean
    public boolean instanciaDB() {
        if (ddl.equals("create-drop")) {
            this.dbService.instanciaDB();
        }

        return false;
    }

}
