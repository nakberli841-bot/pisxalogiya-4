package com.psychology.demo.service;


import com.psychology.demo.dto.*;
import com.psychology.demo.entity.*;
import com.psychology.demo.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PsychologicalTestServiceTest {

    @Mock
    private PsychologicalTestRepository testRepository;
    @Mock
    private AnswerOptionRepository optionRepository;
    @Mock
    private TestResultRangeRepository resultRangeRepository;

    @InjectMocks
    private PsychologicalTestService testService;

    private PsychologicalTest testEntity;
    private AnswerOption option1;
    private AnswerOption option2;

    @BeforeEach
    void setUp() {

        testEntity = new PsychologicalTest();
        testEntity.setId(1L);
        testEntity.setName("Stress Testi");
        testEntity.setQuestions(List.of()); // Boş siyahı və ya mock suallar

        option1 = new AnswerOption();
        option1.setId(10L);
        option1.setPointValue(5);

        option2 = new AnswerOption();
        option2.setId(11L);
        option2.setPointValue(10);
    }

    @Test
    void getTestDetails_Success() {

        when(testRepository.findFullTestDetails(1L)).thenReturn(Optional.of(testEntity));


        PsychologicalTestDTO result = testService.getTestDetails(1L);


        assertNotNull(result);
        assertEquals("Stress Testi", result.getName());
        verify(testRepository).findFullTestDetails(1L);
    }

    @Test
    void submitTest_CalculationSuccess() {

        TestSubmissionRequest request = new TestSubmissionRequest();
        request.setTestId(1L);
        request.setSelectedOptionIds(List.of(10L, 11L));

        TestResultRange range = new TestResultRange();
        range.setRiskLevel("Yüksək");
        range.setRecommendation("Mütəxəssisə müraciət edin");

        when(optionRepository.findAllById(request.getSelectedOptionIds())).thenReturn(List.of(option1, option2));

        when(resultRangeRepository.findByPsychologicalTestIdAndScoreBetween(1L, 15)).thenReturn(Optional.of(range));


        TestResultResponse response = testService.submitTest(request);


        assertNotNull(response);
        assertEquals(15, response.getTotalScore());
        assertEquals("Yüksək", response.getRiskLevel());
        verify(resultRangeRepository).findByPsychologicalTestIdAndScoreBetween(1L, 15);
    }

    @Test
    void submitTest_ShouldThrowException_WhenRangeNotFound() {

        TestSubmissionRequest request = new TestSubmissionRequest();
        request.setTestId(1L);
        request.setSelectedOptionIds(List.of(10L));

        when(optionRepository.findAllById(any())).thenReturn(List.of(option1));
        when(resultRangeRepository.findByPsychologicalTestIdAndScoreBetween(anyLong(), anyInt())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            testService.submitTest(request);
        });

        assertEquals("Bu bal üçün nəticə diapazonu təyin edilməyib", exception.getMessage());
    }
}