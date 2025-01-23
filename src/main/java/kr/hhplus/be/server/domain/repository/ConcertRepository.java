package kr.hhplus.be.server.domain.repository;

import kr.hhplus.be.server.domain.model.Seat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConcertRepository {
    boolean existsById(Long concertId); // 사용자 ID 존재 여부 확인

    boolean isSeatTaken(Long scheduleId, Long seatId, Integer seatNumber); //좌석 중복확인
    Optional<List<String>> findAvailableDatesByConcertId(Long concertId);

    List<Seat> findAvailableSeatsByConcertIdAndDate(Long concertId, LocalDate date);
}
