package com.example.capstone3.DTO_out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ObjectionReportDTO {

    private long totalObjections;
    private long approvedObjections;
    private long rejectedObjections;
    private double approvalRate;
}
