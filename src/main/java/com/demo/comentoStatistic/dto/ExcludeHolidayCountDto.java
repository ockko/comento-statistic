package com.demo.comentoStatistic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExcludeHolidayCountDto {
    private String startDate;
    private String endDate;
    private long totCnt;
}
