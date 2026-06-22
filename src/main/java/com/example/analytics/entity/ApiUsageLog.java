package com.example.analytics.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "api_usage_log",
        indexes = {
                @Index(
                        name = "idx_api_id",
                        columnList = "apiId"
                ),
                @Index(
                        name = "idx_timestamp",
                        columnList = "timestamp"
                ),
                @Index(
                        name = "idx_api_timestamp",
                        columnList = "apiId,timestamp"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiUsageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiId;

    private String endpoint;

    private long latency;

    private int statusCode;

    private double cost;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}