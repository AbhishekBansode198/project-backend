package onedeoleela.onedeoleela.Service;

import lombok.RequiredArgsConstructor;
import onedeoleela.onedeoleela.Entity.Trip;
import onedeoleela.onedeoleela.Entity.User;
import onedeoleela.onedeoleela.Entity.Vehicle;
import onedeoleela.onedeoleela.Entity.VehicleActivityTrack;
import onedeoleela.onedeoleela.Repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepo;
    private final UserRepository userRepo;
    private final VehicleRepository vehicleRepo;
    public  final VehicleActivityTrackRepository vehicleActivityTrackRepo;
    public Trip createTrip(Trip trip) {

        User user = userRepo.findByFullName(trip.getDriverName())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Vehicle vehicle = vehicleRepo.findByVehicleNumber(trip.getVehicleNumber())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        trip.setDriverECode(user.getECode().intValue());
        trip.setDriverId(user.getId());
        trip.setVehicleId(vehicle.getId());
        trip.setStatus("ASSIGNED");

        return tripRepo.save(trip);
    }

    public List<Trip> getAllTrips() {
        return tripRepo.findAll();
    }

    public List<Trip> getTripsByDriver(Long driverId) {
        return tripRepo.findByDriverId(driverId);
    }

    public List<Trip> getTripsByVehicle(Long vehicleId) {
        return tripRepo.findByVehicleId(vehicleId);
    }

    public List<Trip> getTripsByStatus(String status) {
        return tripRepo.findByStatus(status);
    }

    public List<Trip> getTripsForDriverToday(Integer driverECode) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime startOfTomorrow = today.plusDays(1).atStartOfDay();

        // Fetch all trips for today
        List<Trip> tripsToday = tripRepo.findByDriverECodeAndTripDateBetween(driverECode, startOfDay, startOfTomorrow);

        // Get trip IDs
        List<Long> tripIds = tripsToday.stream().map(Trip::getId).toList();

        // Fetch VehicleActivityTrack entries for these trips


        List<VehicleActivityTrack> activities = vehicleActivityTrackRepo.findByTripIdIn(tripIds);

        // Remove trips that already have activity entries
        List<Long> tripsWithActivity = activities.stream().map(VehicleActivityTrack::getTripId).toList();
        tripsToday.removeIf(trip -> tripsWithActivity.contains(trip.getId()));

        return tripsToday;
    }
    // ✅ Method to get Unique Vehicle Numbers
    public List<String> getUniqueVehiclesForDriver(Integer eCode) {
        return tripRepo.findUniqueVehicleNumbersByECode(eCode);
    }
    public List<Trip> getTodayPendingTrips(Integer eCode) {

        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        return tripRepo.findTodayPendingTrips(eCode, startOfDay, endOfDay);
    }
}
