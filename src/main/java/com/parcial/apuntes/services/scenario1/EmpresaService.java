package com.parcial.apuntes.services.scenario1;

import com.parcial.apuntes.models.scenario1.Empresa;
import com.parcial.apuntes.repositories.scenario1.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * ESCENARIO 1: Servicio para Empresa
 * 
 * @Transactional: gestiona transacciones automáticamente
 * Los métodos sin @Transactional son de lectura (solo consultas)
 */
@Service
public class EmpresaService {
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    /**
     * Crear una nueva empresa
     * @Transactional(readOnly = false) indica escritura a BD
     */
    @Transactional
    public Empresa crearEmpresa(Empresa empresa) {
        // Validación
        if (empresa.getNombre() == null || empresa.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la empresa es obligatorio");
        }
        
        // Verificar que no exista otra empresa con el mismo nombre
        if (empresaRepository.existsByNombre(empresa.getNombre())) {
            throw new IllegalArgumentException("Ya existe una empresa con este nombre");
        }
        
        return empresaRepository.save(empresa);
    }
    
    /**
     * Obtener empresa por ID
     */
    public Empresa obtenerEmpresa(Long id) {
        return empresaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con ID: " + id));
    }
    
    /**
     * Obtener todas las empresas
     */
    public List<Empresa> obtenerTodas() {
        return empresaRepository.findAll();
    }
    
    /**
     * Buscar empresas por sector
     */
    public List<Empresa> obtenerPorSector(String sector) {
        if (sector == null || sector.isBlank()) {
            throw new IllegalArgumentException("El sector no puede estar vacío");
        }
        return empresaRepository.obtenerPorSector(sector);
    }
    
    /**
     * Actualizar una empresa existente
     */
    @Transactional
    public Empresa actualizarEmpresa(Long id, Empresa empresaActualizada) {
        Empresa empresa = obtenerEmpresa(id); // Valida existencia
        
        if (empresaActualizada.getNombre() != null && !empresaActualizada.getNombre().isBlank()) {
            empresa.setNombre(empresaActualizada.getNombre());
        }
        if (empresaActualizada.getCiudad() != null) {
            empresa.setCiudad(empresaActualizada.getCiudad());
        }
        if (empresaActualizada.getSector() != null) {
            empresa.setSector(empresaActualizada.getSector());
        }
        
        return empresaRepository.save(empresa);
    }
    
    /**
     * Eliminar una empresa
     * Esto eliminará automáticamente sus empleados si orphanRemoval=true
     */
    @Transactional
    public void eliminarEmpresa(Long id) {
        Empresa empresa = obtenerEmpresa(id);
        empresaRepository.delete(empresa);
    }
}
