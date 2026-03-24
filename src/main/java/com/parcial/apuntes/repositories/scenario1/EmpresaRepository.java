package com.parcial.apuntes.repositories.scenario1;

import com.parcial.apuntes.models.scenario1.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * ESCENARIO 1: Repositorio con consultas personalizadas
 * 
 * JpaRepository proporciona CRUD básico automáticamente:
 * - save(), findById(), findAll(), delete(), etc.
 */
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
    /**
     * Búsqueda por derivación de nombre (Query by Method Name Derivation)
     * Spring genera la consulta automáticamente
     */
    Optional<Empresa> findByNombre(String nombre);
    
    /**
     * Búsqueda múltiple por atributos
     */
    List<Empresa> findByNombreContainingIgnoreCase(String nombre);
    
    List<Empresa> findByCiudad(String ciudad);
    
    /**
     * @Query con JPQL (Java Persistence Query Language)
     * Consulta personalizada usando anotación
     */
    @Query("SELECT e FROM Empresa e WHERE e.sector = :sector ORDER BY e.nombre ASC")
    List<Empresa> obtenerPorSector(@Param("sector") String sector);
    
    /**
     * @Query con SQL nativo
     * Útil para consultas complejas que JPQL no puede hacer fácilmente
     */
    @Query(value = "SELECT * FROM empresa WHERE LOWER(ciudad) = LOWER(:ciudad)", nativeQuery = true)
    List<Empresa> buscarPorCiudadNativo(@Param("ciudad") String ciudad);
    
    /**
     * Consulta que cuenta resultados
     */
    long countBySector(String sector);
    
    /**
     * Consulta que verifica existencia
     */
    boolean existsByNombre(String nombre);
}
