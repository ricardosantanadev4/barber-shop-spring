package com.dio.barber_shop_spring.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dio.barber_shop_spring.models.Schedule;
import com.dio.barber_shop_spring.repositories.ScheduleRepository;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule createSchedule(Schedule schedule) {
        if (this.verifyScheduleExists(schedule)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horário indisponível! Escolha outro horário.");
        }

        return this.scheduleRepository.save(schedule);
    }

    public boolean verifyScheduleExists(Schedule schedule) {
        return this.scheduleRepository.existsByInicioOrFimOrData(schedule.getInicio(), schedule.getFim(),
                schedule.getData());
    }

    public Page<Schedule> agedamentosPaginados(int pageIndex, int pageSize, String filter) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        if (this.verificarStrigVaziaOuNula(filter)) {
            Page<Schedule> schedulePage = this.scheduleRepository.findAll(pageable);
            return schedulePage;
        }

        return this.scheduleRepository.filterSchedules(filter, pageable);
    }

    public boolean verificarStrigVaziaOuNula(String string) {
        return string == null || string.trim().isEmpty();
    }

}
