package com.parcial.apuntes.repositories.scenario2;

import com.parcial.apuntes.models.scenario2.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * ESCENARIO 2: Repositorio para Curso (Many-to-Many inverso)
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    /**
     * Encontrar curso por código
     */
    Curso findByCodigo(String codigo);
    
    /**
     * Encontrar cursos de un estudiante
     */
    @Query("SELECT c FROM Curso c JOIN c.estudiantes e WHERE e.id = :estudianteId")
    List<Curso> cursosPorEstudiante(@Param("estudianteId") Long estudianteId);
    
    /**
     * Encontrar cursos con más de N estudiantes matriculados
     */
    @Query("SELECT c FROM Curso c WHERE SIZE(c.estudiantes) > :cantidad")
    List<Curso> cursosConMuchosEstudiantes(@Param("cantidad") Integer cantidad);
}
