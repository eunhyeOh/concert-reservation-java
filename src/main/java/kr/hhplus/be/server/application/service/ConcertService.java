package kr.hhplus.be.server.application.service;

import kr.hhplus.be.server.api.dto.ConcertScheduleSeatResponseDto;
import kr.hhplus.be.server.domain.model.Seat;
import kr.hhplus.be.server.domain.repository.ConcertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public ConcertScheduleSeatResponseDto getAvailableSeats(Long concertId, LocalDate date) {
        List<Seat> seats =concertRepository.findAvailableSeatsByConcertIdAndDate(concertId, date);

        if(seats.isEmpty()) {
            throw new IllegalArgumentException("좌석 정보가 없습니다.");
        }

        Long scheduleId = seats.get(0).getSeatId();

        //SeatDetail : Domain Model -> DTO로 변환
        List<ConcertScheduleSeatResponseDto.SeatDetail> seatDetails =  seats.stream()
                    .map(seat -> new ConcertScheduleSeatResponseDto.SeatDetail(
                            seat.getSeatId(),
                            seat.getSeatNumber(),
                            seat.getPrice(),
                            seat.getStatus()
                    ))
                    .collect(Collectors.toList());

        return new ConcertScheduleSeatResponseDto(
                concertId,
                scheduleId,
                date.toString(),
                seatDetails
        );
    }

    public List<String> getAvailableDates(Long concertId) {
        return concertRepository.findAvailableDatesByConcertId(concertId)
                .orElseThrow(() -> new IllegalArgumentException("콘서트 정보를 찾을 수 없습니다."));
    }


}
