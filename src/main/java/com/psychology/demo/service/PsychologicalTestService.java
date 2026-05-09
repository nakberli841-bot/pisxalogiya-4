package com.psychology.demo.service;

import com.psychology.demo.dto.*;
import com.psychology.demo.entity.AnswerOption;
import com.psychology.demo.entity.PsychologicalTest;
import com.psychology.demo.entity.TestResultRange;
import com.psychology.demo.excception.BusinessLogicException;
import com.psychology.demo.excception.ResourceNotFoundException;
import com.psychology.demo.repo.AnswerOptionRepository;
import com.psychology.demo.repo.PsychologicalTestRepository;
import com.psychology.demo.repo.QuestionRepository;
import com.psychology.demo.repo.TestResultRangeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PsychologicalTestService {

    private final PsychologicalTestRepository testRepository;
    private final AnswerOptionRepository optionRepository;
    private final TestResultRangeRepository resultRangeRepository;


    public PsychologicalTestDTO getTestDetails(Long testId) {
        PsychologicalTest test = testRepository.findFullTestDetails(testId).orElseThrow(() -> new ResourceNotFoundException("test tapilmadi"));
        return PsychologicalTestDTO.builder()
                .id(test.getId())
                .name(test.getName())
                .description(test.getDescription())
                .durationMinutes(test.getDurationMinutes())
                .questions(test.getQuestions().stream().map(q ->
                        QuestionDTO.builder()
                                .id(q.getId())
                                .questionText(q.getQuestionText())
                                .options(q.getOptions().stream().map(o ->
                                        AnswerOptionDTO.builder()
                                                .id(o.getId())
                                                .optionText(o.getOptionText())
                                                .build()
                                ).toList())
                                .build()
                ).toList())
                .build();
    }


    @Transactional
    public TestResultResponse submitTest(TestSubmissionRequest request) {

        List<AnswerOption> chosenOptions = optionRepository.findAllById(request.getSelectedOptionIds());

        int totalScore = chosenOptions.stream()
                .mapToInt(AnswerOption::getPointValue)
                .sum();


        TestResultRange resultRange = resultRangeRepository
                .findByPsychologicalTestIdAndScoreBetween(request.getTestId(), totalScore)
                .orElseThrow(() -> new BusinessLogicException("Bu bal üçün nəticə diapazonu təyin edilməyib"));


        return TestResultResponse.builder()
                .totalScore(totalScore)
                .riskLevel(resultRange.getRiskLevel())
                .recommendation(resultRange.getRecommendation())
                .build();
    }
}