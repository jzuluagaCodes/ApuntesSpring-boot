package com.parcial.apuntes.models.scenario1;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * ESCENARIO 1: Relación One-to-Many Simple
 * 
 * Una Empresa tiene muchos Empleados
 * Relación: Empresa (1) ----< Empleado (N)
 */
@Entity
@Table(name = "empresa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empresa")
    @SequenceGenerator(name = "seq_empresa", initialValue = 1)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    private String ciudad;
    private String sector;
    
    /**
     * Relación One-to-Many
     * mappedBy: indica que Empleado maneja la FK
     * cascade: cuando se elimina Empresa, se eliminan sus Empleados
     * orphanRemoval: elimina Empleados huérfanos
     */
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Empleado> empleados;
}
