package kr.hhplus.be.server.api.concert.domain.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertResult;
import kr.hhplus.be.server.api.concert.domain.dto.ScheduleResult;
import kr.hhplus.be.server.api.concert.domain.dto.SeatResult;
import kr.hhplus.be.server.api.concert.domain.entity.Concert;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeatStatus;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertJpaRepository;
import kr.hhplus.be.server.api.concert.domain.repository.ScheduleJpaRepository;
import kr.hhplus.be.server.api.concert.domain.repository.SeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConcertService {

    private final ConcertJpaRepository concertJpaRepository;
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final SeatJpaRepository seatJpaRepository;


    public List<ConcertResult> getConcertList() {
        List<Concert> concertList = concertJpaRepository.findAll();

        if(concertList.isEmpty()) {
            throw new IllegalArgumentException("예매 가능한 콘서트가 없습니다.");
        }

        return concertList.stream()
                .map(concert -> new ConcertResult(
                        concert.getId(),
                        concert.getTitle(),
                        concert.getDescription(),
                        concert.getCreatedAt()))
                .toList();
    }


    /**
     * 콘서트 스케줄 조회
     * */
    public List<ScheduleResult> getScheduleList(Long concertId) {

        List<ConcertSchedule> scheduleList = scheduleJpaRepository.findAllByConcertId(concertId);

        if(scheduleList.isEmpty()) {
            throw new IllegalArgumentException("콘서트 정보를 찾을 수 없습니다.");
        }

        return scheduleList.stream()
                .map(schedule -> new ScheduleResult(
                        schedule.getId(),
                        schedule.getConcertId(),
                        schedule.getMaxCapacity(),
                        schedule.getStartDt(),
                        schedule.getEndDt(),
                        schedule.getCreatedAt()))
                .toList();
    }


    /**
     * 스케줄 좌석 리스트 조회
     * */
    public List<SeatResult> getSeatList(Long scheduleId) {
        List<ConcertSeat> seatList = seatJpaRepository.findAllByScheduleIdAndStatus(scheduleId, ConcertSeatStatus.AVAILABLE);

        if(seatList.isEmpty()) {
            throw new IllegalArgumentException("좌석 정보가 없습니다.");
        }

        //Entity -> Record 변환
        return seatList.stream()
                        .map(seat -> new SeatResult(
                                seat.getId(),
                                seat.getScheduleId(),
                                seat.getSeatNumber(),
                                seat.getStatus(),
                                seat.getPrice(),
                                seat.getCreatedAt()))
                        .toList();
    }

    /**
     * 좌석 정보 조회
     * */
    public ConcertSeat getSeat(Long seatId) {
        return seatJpaRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("좌석 정보가 없습니다."));
    }


    /**
     * 좌석 확정 처리
     * */
    @Transactional
    public void confirmSeat(ConcertSeat seat) {
        seat.confirmReservation();
        seatJpaRepository.save(seat);
    }
}
