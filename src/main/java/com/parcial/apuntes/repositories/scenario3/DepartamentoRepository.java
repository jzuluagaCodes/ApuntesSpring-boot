package com.parcial.apuntes.repositories.scenario3;

import com.parcial.apuntes.models.scenario3.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * ESCENARIO 3: Repositorio para Departamento (Autorreferencial)
 */
@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    
    /**
     * Encontrar un departamento por nombre
     */
    Departamento findByNombre(String nombre);
    
    /**
     * Encontrar departamentos sin padre (raíces del árbol)
     */
    @Query("SELECT d FROM Departamento d WHERE d.departamentoPadre IS NULL")
    List<Departamento> departamentosRaiz();
    
    /**
     * Encontrar departamentos subordinados a uno específico
     */
    @Query("SELECT d FROM Departamento d WHERE d.departamentoPadre.id = :padreId")
    List<Departamento> departamentosSubordinados(@Param("padreId") Long padreId);
    
    /**
     * Encontrar la profundidad de un departamento en el árbol
     * (Nota: esto es más complejo en JPQL, se haría mejor con CONNECT BY o en código)
     */
    @Query(value = "WITH RECURSIVE dept_tree AS (" +
           "  SELECT id, nombre, departamento_padre_id, 0 as nivel " +
           "  FROM departamento WHERE id = :deptId " +
           "  UNION ALL " +
           "  SELECT d.id, d.nombre, d.departamento_padre_id, dt.nivel + 1 " +
           "  FROM departamento d " +
           "  JOIN dept_tree dt ON d.id = dt.departamento_padre_id " +
           ") SELECT MAX(nivel) FROM dept_tree", nativeQuery = true)
    Integer obtenerProfundidad(@Param("deptId") Long deptId);
}
