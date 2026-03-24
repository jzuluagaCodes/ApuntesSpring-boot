package com.parcial.apuntes.models.scenario4;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ESCENARIO 4: Llave Compuesta con @Embeddable y @EmbeddedId
 * 
 * ProductoTienda usa una llave compuesta formada por:
 * - producto_id
 * - tienda_id
 * 
 * Esto es útil para tablas de unión con atributos adicionales
 */
@Entity
@Table(name = "producto_tienda")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoTienda {
    
    /**
     * @EmbeddedId: incluye la llave compuesta como objeto embebido
     * Se usa cuando la llave compuesta tiene sus propias reglas de igualdad
     */
    @EmbeddedId
    private ProductoTiendaPK id;
    
    /**
     * Relaciones Many-to-One hacia las tablas referentes
     * @MapsId: vincula los campos del EmbeddedId con estas relaciones
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", insertable = false, updatable = false)
    private Producto producto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tienda_id", insertable = false, updatable = false)
    private Tienda tienda;
    
    // Atributos adicionales de la tabla intermedia
    @Column(nullable = false)
    private Integer stock;
    
    private Double precioLocal;
}
