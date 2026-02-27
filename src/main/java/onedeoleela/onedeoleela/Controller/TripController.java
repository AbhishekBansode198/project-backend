package onedeoleela.onedeoleela.Controller;

import lombok.RequiredArgsConstructor;
import onedeoleela.onedeoleela.Entity.Driver;
import onedeoleela.onedeoleela.Entity.Trip;
import onedeoleela.onedeoleela.Entity.User;
import onedeoleela.onedeoleela.Entity.Vehicle;
import onedeoleela.onedeoleela.Repository.DriverRepository;
import onedeoleela.onedeoleela.Repository.TripRepository;
import onedeoleela.onedeoleela.Repository.UserRepository;
import onedeoleela.onedeoleela.Repository.VehicleRepository;
import onedeoleela.onedeoleela.Service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TripController {

    private final TripService tripService;

    // ✅ CREATE TRIP (Use Service)
    @PostMapping("/planned")
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/driver/{driverId}")
    public List<Trip> getTripsByDriver(@PathVariable Long driverId) {
        return tripService.getTripsByDriver(driverId);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<Trip> getTripsByVehicle(@PathVariable Long vehicleId) {
        return tripService.getTripsByVehicle(vehicleId);
    }

    @GetMapping("/trip/{eCode}/today")
    public List<Trip> getDriverTripsToday(@PathVariable Long eCode) {
        return tripService.getTripsForDriverToday(Math.toIntExact(eCode));
    }

    @GetMapping("/status/{status}")
    public List<Trip> getTripsByStatus(@PathVariable String status) {
        return tripService.getTripsByStatus(status);
    }

    @GetMapping("/driver/{eCode}/unique-vehicles")
    public List<String> getUniqueVehicles(@PathVariable Integer eCode) {
        return tripService.getUniqueVehiclesForDriver(eCode);
    }
    @GetMapping("/today-pending/{eCode}")
    public ResponseEntity<List<Trip>> getTodayPendingTrips(@PathVariable Integer eCode) {
        return ResponseEntity.ok(tripService.getTodayPendingTrips(eCode));
    }
}