package com.parcial.apuntes.services.scenario3;

import com.parcial.apuntes.models.scenario3.Departamento;
import com.parcial.apuntes.repositories.scenario3.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * ESCENARIO 3: Servicio para Departamento (Relación Autorreferencial)
 */
@Service
public class DepartamentoService {
    
    @Autowired
    private DepartamentoRepository departamentoRepository;
    
    /**
     * Crear un departamento
     */
    @Transactional
    public Departamento crearDepartamento(String nombre, String descripcion, Long idPadre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del departamento es obligatorio");
        }
        
        Departamento departamento = new Departamento();
        departamento.setNombre(nombre);
        departamento.setDescripcion(descripcion);
        
        // Si tiene padre, encontrarlo
        if (idPadre != null) {
            Departamento padre = departamentoRepository.findById(idPadre)
                .orElseThrow(() -> new IllegalArgumentException("Departamento padre no encontrado"));
            departamento.setDepartamentoPadre(padre);
        }
        
        return departamentoRepository.save(departamento);
    }
    
    /**
     * Obtener la jerarquía completa de departamentos (raíces)
     */
    public List<Departamento> obtenerDepartamentosRaiz() {
        return departamentoRepository.departamentosRaiz();
    }
    
    /**
     * Obtener departamentos subordinados a uno específico
     */
    public List<Departamento> obtenerSubordinados(Long idDepartamento) {
        // Verifica existencia
        departamentoRepository.findById(idDepartamento)
            .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));
        
        return departamentoRepository.departamentosSubordinados(idDepartamento);
    }
    
    /**
     * Mover un departamento bajo otro padre
     */
    @Transactional
    public void moverDepartamento(Long idDepartamento, Long idNuevoPadre) {
        Departamento departamento = departamentoRepository.findById(idDepartamento)
            .orElseThrow(() -> new IllegalArgumentException("Departamento no encontrado"));
        
        if (idNuevoPadre != null) {
            Departamento nuevoPadre = departamentoRepository.findById(idNuevoPadre)
                .orElseThrow(() -> new IllegalArgumentException("Nuevo padre no encontrado"));
            
            // Validación: no asignar un departamento como padre de sí mismo
            if (idDepartamento.equals(idNuevoPadre)) {
                throw new IllegalArgumentException("Un departamento no puede ser padre de sí mismo");
            }
            
            departamento.setDepartamentoPadre(nuevoPadre);
        } else {
            departamento.setDepartamentoPadre(null); // Sin padre (raíz)
        }
        
        departamentoRepository.save(departamento);
    }
}
