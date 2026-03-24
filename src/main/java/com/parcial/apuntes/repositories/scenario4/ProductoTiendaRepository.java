package com.parcial.apuntes.repositories.scenario4;

import com.parcial.apuntes.models.scenario4.ProductoTienda;
import com.parcial.apuntes.models.scenario4.ProductoTiendaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * ESCENARIO 4: Repositorio para Llave Compuesta
 * 
 * Nota: Este repositorio usa ProductoTiendaPK como tipo de llave
 */
@Repository
public interface ProductoTiendaRepository extends JpaRepository<ProductoTienda, ProductoTiendaPK> {
    
    /**
     * Encontrar productos de una tienda específica
     */
    @Query("SELECT pt FROM ProductoTienda pt WHERE pt.tienda.id = :tiendaId")
    List<ProductoTienda> productosPorTienda(@Param("tiendaId") Long tiendaId);
    
    /**
     * Encontrar tiendas que venden un producto
     */
    @Query("SELECT pt FROM ProductoTienda pt WHERE pt.producto.id = :productoId")
    List<ProductoTienda> tiendasPorProducto(@Param("productoId") Long productoId);
    
    /**
     * Encontrar productos con bajo stock
     */
    @Query("SELECT pt FROM ProductoTienda pt WHERE pt.stock < :minimo")
    List<ProductoTienda> productosConBajoStock(@Param("minimo") Integer minimo);
    
    /**
     * Encontrar productos de una tienda en rango de precio
     */
    @Query("SELECT pt FROM ProductoTienda pt " +
           "WHERE pt.tienda.id = :tiendaId " +
           "AND pt.precioLocal BETWEEN :precioMin AND :precioMax")
    List<ProductoTienda> productoPorTiendaYPrecio(
        @Param("tiendaId") Long tiendaId,
        @Param("precioMin") Double precioMin,
        @Param("precioMax") Double precioMax
    );
}
