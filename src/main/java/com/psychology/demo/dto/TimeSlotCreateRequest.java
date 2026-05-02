package com.psychology.demo.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TimeSlotCreateRequest {

    @NotNull(message = "Başlanğıc vaxtı boş ola bilməz")
    @Future(message = "Başlanğıc vaxtı keçmiş zaman ola bilməz")
    private LocalDateTime startTime;

    @NotNull(message = "Bitmə vaxtı boş ola bilməz")
    @Future(message = "Bitmə vaxtı keçmiş zaman ola bilməz")
    private LocalDateTime endTime;


}