package com.demo.comentoStatistic.service;

import com.demo.comentoStatistic.dao.StatisticMapper;
import com.demo.comentoStatistic.dto.AverageCountDto;
import com.demo.comentoStatistic.dto.DeptMonthCountDto;
import com.demo.comentoStatistic.dto.ExcludeHolidayCountDto;
import com.demo.comentoStatistic.dto.DateCountDto;
import com.demo.comentoStatistic.dto.YearCountDto;
import com.demo.comentoStatistic.dto.YearMonthCountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticService {

    @Autowired
    StatisticMapper statisticMapper;

    @Autowired
    private HolidayApiService holidayApiService;

    public YearCountDto getYearLogins(String year) {
        return statisticMapper.selectYearLogin(year);
    }

    public YearMonthCountDto getYearMonthLogins(String year, String month) {
        return statisticMapper.selectYearMonthLogin(year + month);
    }

    public DeptMonthCountDto getDeptMonthLogins(String deptName, String year, String month) {
        return statisticMapper.selectDeptMonthLogin(deptName, year + month);
    }

    public DateCountDto getDateLogins(String year, String month, String day) {
        return statisticMapper.selectDateLogin(year + month + day);
    }

    public AverageCountDto getAverageLogins(String startDate, String endDate) {
        return statisticMapper.selectAverageLogin(startDate, endDate);
    }

    public ExcludeHolidayCountDto getExcludeHolidayLogins(String startDate, String endDate) {
        List<String> rawHolidayList = holidayApiService.fetchHolidays(startDate, endDate);
        List<String> dbFormatHolidayList = new ArrayList<>();
        if (rawHolidayList != null) {
            for (String date : rawHolidayList) {
                if (date.length() == 8) {
                    dbFormatHolidayList.add(date.substring(2));
                }
            }
        }
        String dbStartDate = (startDate.length() == 8) ? startDate.substring(2) : startDate;
        String dbEndDate = (endDate.length() == 8) ? endDate.substring(2) : endDate;
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", dbStartDate);
        params.put("endDate", dbEndDate);
        params.put("holidayList", dbFormatHolidayList);
        long count = statisticMapper.selectExcludeHolidayLogin(params);
        return new ExcludeHolidayCountDto(startDate, endDate, count);
    }
}
