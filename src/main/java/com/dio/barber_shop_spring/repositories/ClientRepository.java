package com.dio.barber_shop_spring.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dio.barber_shop_spring.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

     @Query("SELECT u FROM Client u WHERE " +
                        "LOWER(u.nome) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
                        "LOWER(u.email) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
                        "LOWER(u.telefone) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
                        "CAST(u.id AS string) LIKE CONCAT('%', :filter, '%')")
        Page<Client> filterClients(@Param("filter") String filter, Pageable pageable);

}
