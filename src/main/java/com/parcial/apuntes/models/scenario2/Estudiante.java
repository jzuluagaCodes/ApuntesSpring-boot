package com.parcial.apuntes.models.scenario2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * ESCENARIO 2: Relación Many-to-Many con tabla intermedia
 * 
 * Un Estudiante puede matricularse en muchos Cursos
 * Un Curso puede tener muchos Estudiantes
 */
@Entity
@Table(name = "estudiante")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estudiante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_estudiante")
    @SequenceGenerator(name = "seq_estudiante", initialValue = 1)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String matricula;
    
    private String nombre;
    private String email;
    
    /**
     * Relación Many-to-Many
     * @JoinTable: crea tabla intermedia "matricula" con:
     *   - joinColumns: FK a estudiante_id
     *   - inverseJoinColumns: FK a curso_id
     */
    @ManyToMany
    @JoinTable(
        name = "matricula",
        joinColumns = @JoinColumn(name = "estudiante_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos;
}
