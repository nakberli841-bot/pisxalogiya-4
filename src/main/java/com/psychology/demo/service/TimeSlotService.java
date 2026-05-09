package com.psychology.demo.service;

import com.psychology.demo.dto.TimeSlotCreateRequest;
import com.psychology.demo.dto.TimeSlotResponse;
import com.psychology.demo.entity.Psychologist;
import com.psychology.demo.entity.TimeSlot;
import com.psychology.demo.repo.PsychologistRepository;

import com.psychology.demo.repo.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final PsychologistRepository psychologistRepository;

    @Transactional
    public TimeSlotResponse createSlot(TimeSlotCreateRequest request) {

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Psychologist psychologist = psychologistRepository.findByUserEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Psixoloq profili tapılmadı!"));


        TimeSlot timeSlot = TimeSlot.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .psychologist(psychologist)
                .isBooked(false)//false=yani bu solta hec bir musteri rezerv olunmyaib heleki
                .build();

         timeSlotRepository.save(timeSlot);

         TimeSlotResponse timeSlotResponse = new TimeSlotResponse();
         timeSlotResponse.setStartTime(timeSlot.getStartTime());
         timeSlotResponse.setEndTime(timeSlot.getEndTime());
         return timeSlotResponse;
    }


    public List<TimeSlot> getAvailableSlots() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Psychologist psychologist = psychologistRepository.findByUserEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Psixoloq profili tapılmadı!"));

        return timeSlotRepository.findAllByPsychologistIdAndIsBookedFalse(psychologist.getId());
    }
}