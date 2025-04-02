package com.dio.barber_shop_spring.services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dio.barber_shop_spring.models.Client;
import com.dio.barber_shop_spring.models.Schedule;
import com.dio.barber_shop_spring.repositories.ClientRepository;
import com.dio.barber_shop_spring.repositories.ScheduleRepository;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ClientRepository clientRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ClientRepository clientRepository) {
        this.scheduleRepository = scheduleRepository;
        this.clientRepository = clientRepository;

    }

    public Schedule createSchedule(Schedule schedule) {
        this.verifyScheduleExists(schedule);
        this.validarHorario(schedule.getInicio(), schedule.getFim());
        return this.scheduleRepository.save(schedule);
    }

    public boolean verifyScheduleExists(Schedule schedule) {
        if (this.scheduleRepository.existsByInicioOrFimOrData(schedule.getInicio(), schedule.getFim(),
                schedule.getData())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horário indisponível! Escolha outro horário.");
        }

        return true;
    }

    public boolean validarHorario(LocalTime inicio, LocalTime fim) {
        LocalTime horarioEsperado = inicio.plusHours(1); // Adiciona 1 hora ao horário inicial
        if (!fim.equals(horarioEsperado)) {
            throw new IllegalArgumentException("Horário inválido! O horário permitido é " + horarioEsperado);
        }
        return fim.equals(horarioEsperado);
    }

    public Page<Schedule> agedamentosPaginados(int pageIndex, int pageSize, String filter) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        if (this.verificarStrigVaziaOuNula(filter)) {
            Page<Schedule> schedulePage = this.scheduleRepository.findAll(pageable);
            return schedulePage;
        }

        if (this.scheduleFilter(filter, pageIndex, pageSize).getTotalElements() > 0 &&
                this.scheduleRepository.filterSchedules(filter, pageable).getTotalElements() == 0) {
            Page<Schedule> schedulePage = this.scheduleFilter(filter, pageIndex, pageSize);
            return schedulePage;
        }

        return this.scheduleRepository.filterSchedules(filter, pageable);
    }

    public boolean verificarStrigVaziaOuNula(String string) {
        return string == null || string.trim().isEmpty();
    }

    public Page<Schedule> scheduleFilter(String filter, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Client> clientPage = this.clientRepository.filterClients(filter, pageable);

        // Processo para retornar um Page<Schedule>
        List<Schedule> schedules = new ArrayList<>();
        clientPage.get().forEach(c -> schedules.addAll(c.getAgendamentos()));

        Pageable schedulePageable = PageRequest.of(clientPage.getNumber(), clientPage.getSize());

        // Aplicar a paginação manualmente na lista de schedules
        int start = (int) schedulePageable.getOffset();
        int end = Math.min(start + schedulePageable.getPageSize(), schedules.size());

        List<Schedule> paginatedSchedules = schedules.subList(start, end);

        // Criar o Page<Schedule>
        Page<Schedule> schedulePage = new PageImpl<>(paginatedSchedules, schedulePageable, schedules.size());

        return schedulePage;
    }

}