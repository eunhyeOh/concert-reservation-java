package kr.hhplus.be.server.infrastructure.persistence.repository;

import kr.hhplus.be.server.infrastructure.persistence.entity.TbConcert;
import kr.hhplus.be.server.infrastructure.persistence.entity.TbConcertScheduleSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<TbConcert, Long> {


    @Query("SELECT DISTINCT c.date FROM TbConcertSchedule c WHERE c.concertId = :concertId")
    List<String> findAvailableDatesByConcertId(Long concertId);


    @Query("SELECT CSS.scheduleId, CSS.seatId, CSS.seatNumber, CSS.price, CSS.status " +
            "FROM TbConcertSchedule AS CS " +
            "INNER JOIN TbConcertScheduleSeat AS CSS " +
            "ON " +
            "CS.concertId = CSS.concertId " +
            "AND CS.scheduleId = CSS.scheduleId " +
            "WHERE CS.concertId = :concertId AND DATE(CS.date) = :date")
    List<TbConcertScheduleSeat> findAvailableSeatsByConcertIdAndDate(
            @Param("concertId") Long concertId,
            @Param("date") LocalDate date);
}
