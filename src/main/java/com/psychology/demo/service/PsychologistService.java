package com.psychology.demo.service;


import com.psychology.demo.dto.PsychologistProfileRequest;
import com.psychology.demo.entity.Psychologist;
import com.psychology.demo.entity.User;
import com.psychology.demo.repo.PsychologistRepository;
import com.psychology.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class PsychologistService {
    private final PsychologistRepository psychologistRepo;
    private final UserRepository userRepo;

    public String updateOrCreateProfile(PsychologistProfileRequest request, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));


        Psychologist psychologist = psychologistRepo.findByUserEmail(email)
                .orElse(new Psychologist());

        psychologist.setUser(user);
        psychologist.setSpecialty(request.getSpecialty());
        psychologist.setExperienceYears(request.getExperienceYears());
        psychologist.setEducation(request.getEducation());
        psychologist.setBio(request.getBio());
        psychologist.setApproach(request.getApproach());
        psychologist.setLanguages(request.getLanguages());

        psychologistRepo.save(psychologist);
        return "Profil uğurla yeniləndi";
    }

    public Psychologist getMyProfile(String email) {
        return psychologistRepo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Profil hələ yaradılmayıb"));
    }
}