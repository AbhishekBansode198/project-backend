package onedeoleela.onedeoleela.Repository;

import onedeoleela.onedeoleela.Entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByDriverId(Long driverId);

    List<Trip> findByVehicleId(Long vehicleId);

    List<Trip> findByStatus(String status);

    List<Trip> findByDriverECode(Integer driverECode);

    List<Trip> findByDriverECodeAndTripDateBetween(
            Integer driverECode,
            LocalDateTime start,
            LocalDateTime end
    );
    // ✅ Fetch only unique vehicle numbers for a specific driver eCode
    @Query("SELECT DISTINCT t.vehicleNumber FROM Trip t WHERE t.driverECode = :eCode")
    List<String> findUniqueVehicleNumbersByECode(@Param("eCode") Integer eCode);

    @Query("""
    SELECT t FROM Trip t
    WHERE t.driverECode = :eCode
    AND t.tripDate >= :startOfDay
    AND t.tripDate < :endOfDay
    AND t.id NOT IN (
        SELECT ts.trip.id FROM TripStatusUpdate ts
    )
""")
    List<Trip> findTodayPendingTrips(
            @Param("eCode") Integer eCode,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}