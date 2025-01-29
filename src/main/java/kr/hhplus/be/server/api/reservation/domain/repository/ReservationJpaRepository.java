package kr.hhplus.be.server.api.reservation.domain.repository;

import kr.hhplus.be.server.api.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findBySeatIdAndReservedUntillDtAfter(Long seatId, LocalDateTime now);

    List<Reservation> findByReservedUntillDtBefore(LocalDateTime now);
}
