package kr.hhplus.be.server.api.v1.controller;

import kr.hhplus.be.server.application.facade.ConcertFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(ConcertController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConcertFacade concertFacade; //JPA로직은 Mock으로 대체

    @Test
    public void testGetAvailableDates_Success() throws Exception {
        //given :Mock 데이터 정의
        Long concertId = 1L;
        List<String> mockAvailableDates = List.of("2025-02-01","2025-02-02","2025-02-03");

        when(concertFacade.getAvailableDates(eq(concertId), anyString())).thenReturn(mockAvailableDates);

        //then :MockMvc로 요청 실행 및 검증
        mockMvc.perform(get("/api/v1/concerts/{concertId}/available-dates", concertId)
                        .header("Authorization", "Bearer mock-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concertId").value(concertId))
                .andExpect(jsonPath("$.availableDates").isArray())
                .andExpect(jsonPath("$.availableDates[0]").value("2025-02-01"));
    }

    @Test
    public void testGetAvailableDates_NotFound() throws Exception {
        Long concertId = 999L;

        when(concertFacade.getAvailableDates(eq(concertId), anyString()))
                .thenThrow(new IllegalArgumentException("콘서트 정보를 찾을 수 없습니다."));

        mockMvc.perform(get("/api/v1/concerts/{concertId}/available-dates", concertId)
                .header("Authorization", "Bearer mock-token"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("콘서트 정보를 찾을 수 없습니다."));
    }

    @Test
    public void testGetAvailableDates_Unauthorized() throws Exception {
        Long concertId = 1L;

        when(concertFacade.getAvailableDates(eq(concertId), anyString()))
                .thenThrow(new SecurityException("잘못된 토큰 정보입니다."));

        mockMvc.perform(get("/api/v1/concerts/{concertId}/available-dates", concertId)
                        .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("잘못된 토큰 정보입니다."));
    }
}
