package impl.reservationsystemapp.Services;

import impl.reservationsystemapp.Entities.Surface;
import impl.reservationsystemapp.Repositories.SurfaceRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SurfaceService {
    private final SurfaceRepository surfaceRepository;

    public SurfaceService(SurfaceRepository surfaceRepository) {
        this.surfaceRepository = surfaceRepository;
    }

    public Surface createSurface(@Valid Surface surface) {
        return surfaceRepository.save(surface);
    }

    public List<Surface> getAllSurfaces() {
        return surfaceRepository.findAll();
    }

    public Surface getSurfaceById(Long id) {
        return surfaceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Surface with id " + id + " not found"));
    }

    public Surface updateSurface(Long id, Surface updatedSurface) {
        Surface surface = getSurfaceById(id);
        surface.setName(updatedSurface.getName());
        surface.setRentalPrice(updatedSurface.getRentalPrice());
        return surfaceRepository.save(surface);
    }

    public void deleteSurface(Long id) {
        surfaceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Surface with id " + id + " not found"));
        surfaceRepository.deleteById(id);
    }

}