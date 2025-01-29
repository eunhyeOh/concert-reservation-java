package kr.hhplus.be.server.api.concert.domain.repository;

import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatJpaRepository extends JpaRepository<ConcertSeat, Long> {

    List<ConcertSeat> findAllByScheduleIdAndStatus(Long scheduleID, ConcertSeatStatus status);
}
