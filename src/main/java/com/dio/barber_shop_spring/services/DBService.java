package com.dio.barber_shop_spring.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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

        List<LocalTime> inicioList = new ArrayList<>(
                Arrays.asList(
                        LocalTime.of(8, 00),
                        LocalTime.of(9, 00),
                        LocalTime.of(10, 00),
                        LocalTime.of(11, 00),
                        LocalTime.of(14, 00),
                        LocalTime.of(15, 00),
                        LocalTime.of(16, 00),
                        LocalTime.of(17, 00)));

        List<LocalTime> fimList = new ArrayList<>(
                Arrays.asList(
                        LocalTime.of(9, 00),
                        LocalTime.of(10, 00),
                        LocalTime.of(11, 00),
                        LocalTime.of(12, 00),
                        LocalTime.of(15, 00),
                        LocalTime.of(16, 00),
                        LocalTime.of(17, 00),
                        LocalTime.of(18, 00)));

        for (int i = 0; i < numeroDeRegistros; i++) {
            Client client = new Client(
                    null,
                    "Nome" + i,
                    "email" + i + "@email.com",
                    "81999999999", null);

            // Pegando o horário da lista de forma cíclica
            LocalTime inicio = inicioList.get(i % inicioList.size());

            LocalTime fim = fimList.get(i % inicioList.size());

            Schedule schedule = new Schedule(
                    null,
                    inicio,
                    fim,
                    LocalDate.of(2025, 1, 1 + (i % 28)),
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
