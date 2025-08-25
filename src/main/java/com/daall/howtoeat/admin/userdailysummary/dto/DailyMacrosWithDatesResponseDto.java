package com.daall.howtoeat.admin.userdailysummary.dto;

import com.daall.howtoeat.client.userdailysummary.dto.DailyConsumedMacrosResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class DailyMacrosWithDatesResponseDto {
    private List<String> dates; // index 0: prev, 1: next (없으면 null)
    private DailyConsumedMacrosResponseDto macros;

    public DailyMacrosWithDatesResponseDto(List<String> dates, DailyConsumedMacrosResponseDto macros) {
        this.dates = dates;
        this.macros = macros;
    }
}
