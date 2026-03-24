package com.parcial.apuntes.models.scenario3;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * ESCENARIO 3: Relación One-to-Many Autorreferencial
 * 
 * Un Departamento puede tener un Departamento superior
 * Un Departamento puede tener muchos Departamentos subordinados
 */
@Entity
@Table(name = "departamento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Departamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dpto")
    @SequenceGenerator(name = "seq_dpto", initialValue = 1)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    
    /**
     * FK a sí mismo para autorreferencia
     * Relación Many-to-One: muchos departamentos apuntan a uno superior
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_padre_id")
    private Departamento departamentoPadre;
    
    /**
     * Relación One-to-Many: un departamento puede tener muchos subordinados
     * mappedBy apunta al atributo que maneja la relación
     */
    @OneToMany(mappedBy = "departamentoPadre", cascade = CascadeType.ALL)
    private List<Departamento> departamentosSubordinados;
}
