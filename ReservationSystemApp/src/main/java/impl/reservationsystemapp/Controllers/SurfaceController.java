package impl.reservationsystemapp.Controllers;

import impl.reservationsystemapp.Entities.Surface;
import impl.reservationsystemapp.Services.SurfaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surfaces")
public class SurfaceController {

    private final SurfaceService surfaceService;

    public SurfaceController(SurfaceService surfaceService) {
        this.surfaceService = surfaceService;
    }

    @GetMapping
    public ResponseEntity<List<Surface>> getAllSurfaces() {
        List<Surface> surfaces = surfaceService.getAllSurfaces();
        return new ResponseEntity<>(surfaces, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Surface> getSurfaceById(@PathVariable Long id) {
        Surface surface = surfaceService.getSurfaceById(id);
        return new ResponseEntity<>(surface, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Surface> createSurface(@RequestBody Surface surface) {
        Surface createdSurface = surfaceService.createSurface(surface);
        return new ResponseEntity<>(createdSurface, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Surface> updateSurface(@PathVariable Long id, @RequestBody Surface updatedSurface) {
        Surface surface = surfaceService.updateSurface(id, updatedSurface);
        return new ResponseEntity<>(surface, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurface(@PathVariable Long id) {
        surfaceService.deleteSurface(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
