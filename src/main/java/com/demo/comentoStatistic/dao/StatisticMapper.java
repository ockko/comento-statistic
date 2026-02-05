package com.demo.comentoStatistic.dao;

import com.demo.comentoStatistic.dto.AverageCountDto;
import com.demo.comentoStatistic.dto.DateCountDto;
import com.demo.comentoStatistic.dto.DeptMonthCountDto;
import com.demo.comentoStatistic.dto.YearCountDto;
import com.demo.comentoStatistic.dto.YearMonthCountDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface StatisticMapper {

    YearCountDto selectYearLogin(String year);
    YearMonthCountDto selectYearMonthLogin(String yearMonth);
    DeptMonthCountDto selectDeptMonthLogin(String deptName, String yearMonth);
    DateCountDto selectDateLogin(String date);
    AverageCountDto selectAverageLogin(String startDate, String endDate);
    long selectExcludeHolidayLogin(Map<String, Object> params);
}
