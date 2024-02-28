package impl.reservationsystemapp.Services;

import impl.reservationsystemapp.Entities.Court;
import impl.reservationsystemapp.Repositories.CourtRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class CourtService {
    private final CourtRepository courtRepository;

    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public Court createCourt(Court court) {
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
        courtRepository.deleteById(id);
    }
}

