package com.parcial.apuntes.repositories.scenario1;

import com.parcial.apuntes.models.scenario1.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * ESCENARIO 1: Repositorio para Empleado con JOIN a Empresa
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
    /**
     * Búsqueda de empleados por nombre de empresa
     * Spring interpreta la relación automáticamente
     */
    List<Empleado> findByEmpresaNombre(String empresaNombre);
    
    /**
     * Búsqueda con JPQL y JOIN explícito
     * Encuentra empleados de una empresa con salario mayor a X
     */
    @Query("SELECT e FROM Empleado e JOIN e.empresa emp WHERE emp.id = :empresaId AND e.salario > :salario")
    List<Empleado> empleadosPorEmpresaYSalario(
        @Param("empresaId") Long empresaId,
        @Param("salario") Double salario
    );
    
    /**
     * Encontrar el empleado con mayor salario de una empresa
     */
    @Query("SELECT e FROM Empleado e WHERE e.empresa.id = :empresaId ORDER BY e.salario DESC LIMIT 1")
    Empleado empleadoMayorSalario(@Param("empresaId") Long empresaId);
    
    /**
     * Contar empleados por puesto
     */
    long countByPuesto(String puesto);
}
