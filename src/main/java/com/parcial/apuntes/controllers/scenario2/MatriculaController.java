package com.parcial.apuntes.controllers.scenario2;

import com.parcial.apuntes.models.scenario2.Estudiante;
import com.parcial.apuntes.models.scenario2.Curso;
import com.parcial.apuntes.services.scenario2.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ESCENARIO 2: Controlador REST para Matriculación (Many-to-Many)
 */
@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {
    
    @Autowired
    private MatriculaService matriculaService;
    
    /**
     * POST /api/matriculas/estudiante/{estudianteId}/curso/{cursoId}
     * Matricular un estudiante en un curso
     */
    @PostMapping("/estudiante/{estudianteId}/curso/{cursoId}")
    public ResponseEntity<String> matricular(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        try {
            matriculaService.matricularEstudiante(estudianteId, cursoId);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Estudiante matriculado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * DELETE /api/matriculas/estudiante/{estudianteId}/curso/{cursoId}
     * Desmatricular un estudiante de un curso
     */
    @DeleteMapping("/estudiante/{estudianteId}/curso/{cursoId}")
    public ResponseEntity<String> desmatricular(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        try {
            matriculaService.desmatricularEstudiante(estudianteId, cursoId);
            return ResponseEntity.ok("Estudiante desmatriculado");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * GET /api/matriculas/estudiante/{estudianteId}/cursos
     * Obtener cursos de un estudiante
     */
    @GetMapping("/estudiante/{estudianteId}/cursos")
    public ResponseEntity<List<Curso>> obtenerCursosEstudiante(@PathVariable Long estudianteId) {
        try {
            List<Curso> cursos = matriculaService.obtenerCursosEstudiante(estudianteId);
            return ResponseEntity.ok(cursos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /api/matriculas/curso/{cursoId}/estudiantes
     * Obtener estudiantes de un curso
     */
    @GetMapping("/curso/{cursoId}/estudiantes")
    public ResponseEntity<List<Estudiante>> obtenerEstudiantesCurso(@PathVariable Long cursoId) {
        try {
            List<Estudiante> estudiantes = matriculaService.obtenerEstudiantesCurso(cursoId);
            return ResponseEntity.ok(estudiantes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /api/matriculas/estudiante/{estudianteId}/cantidad-cursos
     * Contar cursos de un estudiante
     */
    @GetMapping("/estudiante/{estudianteId}/cantidad-cursos")
    public ResponseEntity<Integer> contarCursos(@PathVariable Long estudianteId) {
        try {
            Integer cantidad = matriculaService.contarCursosEstudiante(estudianteId);
            return ResponseEntity.ok(cantidad);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
