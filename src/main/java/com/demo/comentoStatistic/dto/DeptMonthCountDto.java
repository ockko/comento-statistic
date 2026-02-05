package com.demo.comentoStatistic.dto;

import lombok.Getter;

@Getter
public class DeptMonthCountDto {
    private String deptName;
    private String yearMonth;
    private long totCnt;
}
