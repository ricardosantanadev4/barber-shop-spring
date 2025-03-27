package com.dio.barber_shop_spring.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dio.barber_shop_spring.models.Client;
import com.dio.barber_shop_spring.models.Schedule;
import com.dio.barber_shop_spring.repositories.ClientRepository;
import com.dio.barber_shop_spring.repositories.ScheduleRepository;

@Service
public class DBService {

    private final ClientRepository clientRepository;
    private final ScheduleRepository scheduleRepository;

    public DBService(ClientRepository clientRepository, ScheduleRepository scheduleRepository) {
        this.clientRepository = clientRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public boolean instanciaDB() {

        int numeroDeRegistros = 20;
        for (int i = 0; i < numeroDeRegistros; i++) {
            Client client = new Client(
                    null,
                    "Nome" + i,
                    "email" + i + "@email.com",
                    "81999999999", null);

            Schedule schedule = new Schedule(
                    null,
                    LocalTime.of(i, i),
                    LocalTime.of(1 + i, i),
                    LocalDateTime.of(2025, 01, 1 + i, 00, 00, i++),
                    client);

            List<Schedule> schedules = new ArrayList<>();
            schedules.add(schedule);
            client.setAgendamentos(schedules);
            this.clientRepository.save(client);
            this.scheduleRepository.save(schedule);
        }

        return true;
    }
}
