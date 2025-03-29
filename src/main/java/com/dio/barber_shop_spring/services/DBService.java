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

            // Ajustando para garantir valores válidos para a hora e segundo
            int hora = i % 24; // Garante que a hora fique entre 0 e 23
            int minuto = i % 60; // Garante que o minuto fique entre 0 e 59
            int segundo = i % 60; // Garante que o segundo fique entre 0 e 59

            Schedule schedule = new Schedule(
                    null,
                    LocalTime.of(hora, minuto),
                    LocalTime.of((hora + 1) % 24, minuto), // Evita ultrapassar 23h
                    LocalDateTime.of(2025, 1, 1 + (i % 28), 0, 0, segundo),
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
