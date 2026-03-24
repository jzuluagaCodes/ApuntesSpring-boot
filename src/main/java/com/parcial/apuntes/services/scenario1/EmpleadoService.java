package com.parcial.apuntes.services.scenario1;

import com.parcial.apuntes.models.scenario1.Empleado;
import com.parcial.apuntes.models.scenario1.Empresa;
import com.parcial.apuntes.repositories.scenario1.EmpleadoRepository;
import com.parcial.apuntes.repositories.scenario1.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * ESCENARIO 1: Servicio para Empleado
 */
@Service
public class EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    /**
     * Crear un nuevo empleado
     */
    @Transactional
    public Empleado crearEmpleado(Empleado empleado, Long empresaId) {
        // Validaciones
        if (empleado.getNombre() == null || empleado.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del empleado es obligatorio");
        }
        
        if (empleado.getSalario() == null || empleado.getSalario() <= 0) {
            throw new IllegalArgumentException("El salario debe ser mayor a 0");
        }
        
        // Encontrar la empresa
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));
        
        empleado.setEmpresa(empresa);
        return empleadoRepository.save(empleado);
    }
    
    /**
     * Obtener empleados de una empresa
     */
    public List<Empleado> obtenerEmpleadosPorEmpresa(Long empresaId) {
        // Verifica que la empresa exista
        empresaRepository.findById(empresaId)
            .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));
        
        return empleadoRepository.findByEmpresaNombre(
            empresaRepository.findById(empresaId).get().getNombre()
        );
    }
    
    /**
     * Obtener empleados con salario mínimo de una empresa
     */
    public List<Empleado> obtenerEmpleadosPorSalario(Long empresaId, Double salarioMinimo) {
        return empleadoRepository.empleadosPorEmpresaYSalario(empresaId, salarioMinimo);
    }
    
    /**
     * Aumentar salario a todos los empleados de una empresa
     */
    @Transactional
    public void aumentarSalariosEmpresa(Long empresaId, Double porcentaje) {
        if (porcentaje == null || porcentaje <= 0) {
            throw new IllegalArgumentException("El porcentaje debe ser mayor a 0");
        }
        
        List<Empleado> empleados = obtenerEmpleadosPorEmpresa(empresaId);
        
        for (Empleado emp : empleados) {
            Double nuevoSalario = emp.getSalario() * (1 + porcentaje / 100);
            emp.setSalario(nuevoSalario);
        }
        
        empleadoRepository.saveAll(empleados);
    }
}
