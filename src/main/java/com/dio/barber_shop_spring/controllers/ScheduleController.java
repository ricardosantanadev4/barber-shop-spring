package com.dio.barber_shop_spring.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dio.barber_shop_spring.models.Schedule;
import com.dio.barber_shop_spring.services.ScheduleService;

@RestController
@RequestMapping("agendamentos")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        Schedule body = this.scheduleService.createSchedule(schedule);
        return new ResponseEntity<Schedule>(body, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Schedule>> agedamentosPaginados(@RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String filter) {
        Page<Schedule> schedulePage = this.scheduleService.agedamentosPaginados(pageIndex, pageSize, filter);
        return new ResponseEntity<Page<Schedule>>(schedulePage, HttpStatus.OK);
    }
}
