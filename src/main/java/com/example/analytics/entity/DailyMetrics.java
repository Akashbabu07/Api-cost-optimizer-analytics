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
                        name = "uk_daily_api_time",
                        columnNames = {
                                "apiName",
                                "dayTimestamp"
                        }
                )
        }
)
public class DailyMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiName;

    private long totalHits;

    private double avgResponseTime;

    private double totalCost;

    private long dayTimestamp;
}