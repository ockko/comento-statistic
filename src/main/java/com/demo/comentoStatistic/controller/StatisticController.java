package com.demo.comentoStatistic.controller;

import com.demo.comentoStatistic.dto.AverageCountDto;
import com.demo.comentoStatistic.dto.DeptMonthCountDto;
import com.demo.comentoStatistic.dto.ExcludeHolidayCountDto;
import com.demo.comentoStatistic.dto.DateCountDto;
import com.demo.comentoStatistic.dto.YearCountDto;
import com.demo.comentoStatistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatisticController {

    @Autowired
    StatisticService statisticService;

    @RequestMapping(value = "/api/v1/logins/{year}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<YearCountDto> getYearLoginCount(@PathVariable("year") String year) {
        return ResponseEntity.ok(statisticService.getYearLogins(year));
    }

    @RequestMapping(value = "/api/v1/logins/{year}/{month}", produces = "application/json")
    @ResponseBody
    public Object getYearMonthLoginCount(@PathVariable("year") String year, @PathVariable("month") String month) {
        return ResponseEntity.ok(statisticService.getYearMonthLogins(year, month));
    }

    @RequestMapping(value = "/api/v1/organization/{deptName}/{year}/{month}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<DeptMonthCountDto> getDeptMonthLoginCount(@PathVariable("deptName") String deptName, @PathVariable("year") String year, @PathVariable("month") String month) {
        return ResponseEntity.ok(statisticService.getDeptMonthLogins(deptName, year, month));
    }

    @RequestMapping(value = "/api/v1/logins/{year}/{month}/{day}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<DateCountDto> getDateLoginCount(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("day") String day) {
        return ResponseEntity.ok(statisticService.getDateLogins(year, month, day));
    }

    @RequestMapping(value = "api/v1/average/logins/{startDate}/{endDate}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<AverageCountDto> getAverageLoginCount(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        return ResponseEntity.ok(statisticService.getAverageLogins(startDate, endDate));
    }

    @RequestMapping(value = "api/v1/exclude-holiday/{startDate}/{endDate}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ExcludeHolidayCountDto> getExcludeHolidayCount(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        return ResponseEntity.ok(statisticService.getExcludeHolidayLogins(startDate, endDate));
    }
}
