package kr.hhplus.be.server.api.concert.application;

import kr.hhplus.be.server.api.concert.domain.entity.Concert;
import kr.hhplus.be.server.api.concert.domain.entity.Schedule;
import kr.hhplus.be.server.api.concert.domain.entity.Seat;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertJpaRepository;
import kr.hhplus.be.server.api.concert.domain.repository.ScheduleJpaRepository;
import kr.hhplus.be.server.api.concert.domain.repository.SeatJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private final ConcertJpaRepository concertRepository;
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final SeatJpaRepository seatJpaRepository;

    public ConcertService(ConcertJpaRepository concertRepository, ScheduleJpaRepository scheduleJpaRepository, SeatJpaRepository seatJpaRepository) {
        this.concertRepository = concertRepository;
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.seatJpaRepository = seatJpaRepository;
    }


    public List<Concert> getConcertList() {
        List<Concert> concertList = concertRepository.findAll();

        if(concertList.isEmpty()) {
            throw new IllegalArgumentException("예매 가능한 콘서트가 없습니다.");
        }

        return concertList;
    }


    /**
     * 콘서트 스케줄 조회
     * */
    public List<Schedule> getScheduleList(Long concertId) {

        List<Schedule> scheduleList = scheduleJpaRepository.findAllByConcertId(concertId);

        if(scheduleList.isEmpty()) {
            throw new IllegalArgumentException("콘서트 정보를 찾을 수 없습니다.");
        }

        return scheduleList;
    }


    /**
     * 스케줄 좌석 조회
     * */
    public List<Seat> getSeatList(Long scheduleId) {
        List<Seat> seatList = seatJpaRepository.findAllByScheduleIdAndStatus(scheduleId, "AVAILABLE");

        if(seatList.isEmpty()) {
            throw new IllegalArgumentException("좌석 정보가 없습니다.");
        }

        return seatList;
    }




}
