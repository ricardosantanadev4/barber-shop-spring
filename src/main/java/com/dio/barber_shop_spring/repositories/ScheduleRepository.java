package com.dio.barber_shop_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dio.barber_shop_spring.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

}
