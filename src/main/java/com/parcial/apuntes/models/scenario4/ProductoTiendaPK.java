package com.parcial.apuntes.models.scenario4;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * ESCENARIO 4: Clase que define la Llave Compuesta
 * 
 * @Embeddable: permite que esta clase sea usada como llave primaria embebida
 * Debe implementar Serializable para poder ser persistida
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoTiendaPK implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "producto_id")
    private Long productoId;
    
    @Column(name = "tienda_id")
    private Long tiendaId;
}
