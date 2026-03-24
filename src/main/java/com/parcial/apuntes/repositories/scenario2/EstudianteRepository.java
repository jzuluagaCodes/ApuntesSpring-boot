package com.parcial.apuntes.repositories.scenario2;

import com.parcial.apuntes.models.scenario2.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * ESCENARIO 2: Repositorio para Estudiante (Many-to-Many)
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
    /**
     * Encontrar estudiante por matrícula
     */
    Estudiante findByMatricula(String matricula);
    
    /**
     * Encontrar estudiantes de un curso específico
     * Accede a través de la relación many-to-many
     */
    @Query("SELECT e FROM Estudiante e JOIN e.cursos c WHERE c.id = :cursoId")
    List<Estudiante> estudiantesPorCurso(@Param("cursoId") Long cursoId);
    
    /**
     * Encontrar estudiantes que estén en más de N cursos
     */
    @Query("SELECT e FROM Estudiante e WHERE SIZE(e.cursos) > :cantidad")
    List<Estudiante> estudiantesConMasDeCursos(@Param("cantidad") Integer cantidad);
}
