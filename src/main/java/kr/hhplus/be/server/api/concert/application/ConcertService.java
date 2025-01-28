package kr.hhplus.be.server.api.concert.application;

import kr.hhplus.be.server.api.concert.domain.entity.Concert;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertJpaRepository;
import kr.hhplus.be.server.api.concert.domain.repository.ScheduleJpaRepository;
import kr.hhplus.be.server.api.concert.domain.repository.SeatJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private final ConcertJpaRepository concertJpaRepository;
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final SeatJpaRepository seatJpaRepository;

    public ConcertService(ConcertJpaRepository concertJpaRepository, ScheduleJpaRepository scheduleJpaRepository, SeatJpaRepository seatJpaRepository) {
        this.concertJpaRepository = concertJpaRepository;
        this.scheduleJpaRepository = scheduleJpaRepository;
        this.seatJpaRepository = seatJpaRepository;
    }


    public List<Concert> getConcertList() {
        List<Concert> concertList = concertJpaRepository.findAll();

        if(concertList.isEmpty()) {
            throw new IllegalArgumentException("예매 가능한 콘서트가 없습니다.");
        }

        return concertList;
    }


    /**
     * 콘서트 스케줄 조회
     * */
    public List<ConcertSchedule> getScheduleList(Long concertId) {

        List<ConcertSchedule> scheduleList = scheduleJpaRepository.findAllByConcertId(concertId);

        if(scheduleList.isEmpty()) {
            throw new IllegalArgumentException("콘서트 정보를 찾을 수 없습니다.");
        }

        return scheduleList;
    }


    /**
     * 스케줄 좌석 조회
     * */
    public List<ConcertSeat> getSeatList(Long scheduleId) {
        List<ConcertSeat> seatList = seatJpaRepository.findAllByScheduleIdAndStatus(scheduleId, "AVAILABLE");

        if(seatList.isEmpty()) {
            throw new IllegalArgumentException("좌석 정보가 없습니다.");
        }

        return seatList;
    }




}
