package com.parcial.apuntes.models.scenario2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ESCENARIO 2: Relación Many-to-Many correspondiente a Estudiante
 * 
 * Muchos Cursos pueden tener muchos Estudiantes
 */
@Entity
@Table(name = "curso")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_curso")
    @SequenceGenerator(name = "seq_curso", initialValue = 1)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String codigo;
    
    private String nombre;
    private Integer creditos;
    
    /**
     * Lado inverso de la relación Many-to-Many
     * mappedBy: indica que Estudiante gestiona la tabla intermedia
     * @JsonIgnore: evita serialización circular
     */
    @ManyToMany(mappedBy = "cursos")
    @JsonIgnore
    private List<Estudiante> estudiantes;
}
