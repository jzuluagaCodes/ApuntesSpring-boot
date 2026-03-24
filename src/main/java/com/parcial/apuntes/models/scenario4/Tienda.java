package com.parcial.apuntes.models.scenario4;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "tienda")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tienda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tienda")
    @SequenceGenerator(name = "seq_tienda", initialValue = 1)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    private String ubicacion;
    
    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
    private List<ProductoTienda> productos;
}
