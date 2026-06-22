package com.example.analytics.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private int totalHourlyRecords;
    private int totalDailyRecords;
}