package impl.reservationsystemapp.Services;

import impl.reservationsystemapp.Entities.Court;
import impl.reservationsystemapp.Repositories.CourtRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for managing courts in the application.
 *
 * @author Martin Bjaloň
 */

@Service
public class CourtService {
    private final CourtRepository courtRepository;

    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public Court createCourt(@Valid Court court) {
        return courtRepository.save(court);
    }

    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }

    public Court getCourtById(Long id) {
        return courtRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Court with id " + id + " not found"));
    }

    public Court updateCourt(Long id, Court updatedCourt) {
        Court court = getCourtById(id);
        court.setName(updatedCourt.getName());
        court.setSurface(updatedCourt.getSurface());
        return courtRepository.save(court);
    }

    public void deleteCourt(Long id) {
        courtRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Court with id " + id + " not found"));
        courtRepository.deleteById(id);
    }
}

