package impl.reservationsystemapp.Controllers;

import impl.reservationsystemapp.Entities.Court;
import impl.reservationsystemapp.Services.CourtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courts")
public class CourtController {
    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping
    public ResponseEntity<List<Court>> getAllCourts() {
        List<Court> courts = courtService.getAllCourts();
        return new  ResponseEntity<>(courts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Court> getCourtById(@PathVariable Long id) {
        Court court = courtService.getCourtById(id);
        return new ResponseEntity<>(court, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Court> createCourt(@RequestBody Court court) {
        Court createdCourt = courtService.createCourt(court);
        return new ResponseEntity<>(createdCourt, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Court> updateCourt(@PathVariable Long id, @RequestBody Court updatedCourt) {
        Court court = courtService.updateCourt(id, updatedCourt);
        return new ResponseEntity<>(court, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourt(@PathVariable Long id) {
        courtService.deleteCourt(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}