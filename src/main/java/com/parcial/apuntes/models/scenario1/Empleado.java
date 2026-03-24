package com.parcial.apuntes.models.scenario1;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ESCENARIO 1: Relación Many-to-One correspondiente a Empresa
 * 
 * Muchos Empleados pertenecen a una Empresa
 */
@Entity
@Table(name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empleado")
    @SequenceGenerator(name = "seq_empleado", initialValue = 1)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String puesto;
    private Double salario;
    
    /**
     * Relación Many-to-One
     * @JoinColumn: especifica la columna FK en la tabla empleado
     * @JsonIgnore: evita serialización circular en JSON
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonIgnore
    private Empresa empresa;
}
