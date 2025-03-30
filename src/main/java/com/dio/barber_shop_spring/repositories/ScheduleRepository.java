package com.dio.barber_shop_spring.repositories;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dio.barber_shop_spring.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

        @Query("SELECT u FROM Schedule u WHERE " +
                        "LOWER(FUNCTION('to_char', u.inicio, 'HH24:MI:SS')) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
                        "LOWER(FUNCTION('to_char', u.fim, 'HH24:MI:SS')) LIKE LOWER(CONCAT('%', :filter, '%')) OR " +
                        "LOWER(FUNCTION('to_char', u.data, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :filter, '%')) OR "
                        +
                        "CAST(u.id AS string) LIKE CONCAT('%', :filter, '%')")
        Page<Schedule> filterSchedules(@Param("filter") String filter, Pageable pageable);

        @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.inicio = :inicio AND s.data =:data OR s.fim = :fim AND s.data = :data")
        boolean existsByInicioOrFimOrData(@Param("inicio") LocalTime inicio,
                        @Param("fim") LocalTime fim,
                        @Param("data") LocalDate data);

}
