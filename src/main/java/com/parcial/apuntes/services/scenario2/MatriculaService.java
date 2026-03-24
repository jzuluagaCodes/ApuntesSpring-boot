package com.parcial.apuntes.services.scenario2;

import com.parcial.apuntes.models.scenario2.Estudiante;
import com.parcial.apuntes.models.scenario2.Curso;
import com.parcial.apuntes.repositories.scenario2.EstudianteRepository;
import com.parcial.apuntes.repositories.scenario2.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * ESCENARIO 2: Servicio para Matriculación (Many-to-Many)
 */
@Service
public class MatriculaService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private CursoRepository cursoRepository;
    
    /**
     * Matricular un estudiante en un curso
     */
    @Transactional
    public void matricularEstudiante(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
            .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        
        Curso curso = cursoRepository.findById(cursoId)
            .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        
        // Verificar que no esté ya matriculado
        if (estudiante.getCursos().contains(curso)) {
            throw new IllegalArgumentException("El estudiante ya está matriculado en este curso");
        }
        
        // Agregar curso a lista de cursos del estudiante
        estudiante.getCursos().add(curso);
        estudianteRepository.save(estudiante);
    }
    
    /**
     * Desmatricular un estudiante de un curso
     */
    @Transactional
    public void desmatricularEstudiante(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
            .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        
        Curso curso = cursoRepository.findById(cursoId)
            .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        
        estudiante.getCursos().remove(curso);
        estudianteRepository.save(estudiante);
    }
    
    /**
     * Obtener todos los cursos de un estudiante
     */
    public List<Curso> obtenerCursosEstudiante(Long estudianteId) {
        return cursoRepository.cursosPorEstudiante(estudianteId);
    }
    
    /**
     * Obtener todos los estudiantes de un curso
     */
    public List<Estudiante> obtenerEstudiantesCurso(Long cursoId) {
        return estudianteRepository.estudiantesPorCurso(cursoId);
    }
    
    /**
     * Contar cuántos cursos tiene un estudiante
     */
    public Integer contarCursosEstudiante(Long estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
            .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        
        return estudiante.getCursos().size();
    }
    
    /**
     * Encontrar estudiantes con muchos cursos
     */
    public List<Estudiante> estudiantesConMasDeCursos(Integer cantidad) {
        return estudianteRepository.estudiantesConMasDeCursos(cantidad);
    }
}
