package kr.hhplus.be.server.infrastructure.persistence.repository;

import kr.hhplus.be.server.domain.model.Seat;
import kr.hhplus.be.server.domain.repository.ConcertRepository;
import kr.hhplus.be.server.infrastructure.persistence.entity.TbConcertScheduleSeat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/// ConcertRepository의 구현체, JPA호출
@Repository
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    public ConcertRepositoryImpl(ConcertJpaRepository concertJpaRepository) {
        this.concertJpaRepository = concertJpaRepository;
    }

    @Override
    public boolean existsById(Long concertId) {
        return false;
    }

    @Override
    public boolean isSeatTaken(Long scheduleId, Long seatId, Integer seatNumber) {
        return false;
    }

    public Optional<List<String>> findAvailableDatesByConcertId(Long concertId){
        List<String> dates = concertJpaRepository.findAvailableDatesByConcertId(concertId);

        return dates.isEmpty() ? Optional.empty() : Optional.of(dates);
    }


    public List<Seat> findAvailableSeatsByConcertIdAndDate(Long concertId, LocalDate date){
        List<TbConcertScheduleSeat> dbResult = concertJpaRepository.findAvailableSeatsByConcertIdAndDate(concertId, date);

        //DB Entity -> Domain Model로 변환
        return dbResult.stream()
                .map(dbEntity -> new Seat(
                        dbEntity.getScheduleId(),
                        dbEntity.getSeatId(),
                        dbEntity.getSeatNumber(),
                        dbEntity.getPrice(),
                        dbEntity.getStatus()
                ))
                .collect(Collectors.toList());
    }
}
