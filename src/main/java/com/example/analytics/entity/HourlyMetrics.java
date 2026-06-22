package com.example.analytics.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_hourly_api_time",
                        columnNames = {
                                "apiName",
                                "hourTimestamp"
                        }
                )
        }
)
public class HourlyMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiName;

    private long totalHits;

    private double avgResponseTime;

    private double totalCost;

    private long hourTimestamp;
}