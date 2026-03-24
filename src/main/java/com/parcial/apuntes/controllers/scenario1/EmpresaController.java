package com.parcial.apuntes.controllers.scenario1;

import com.parcial.apuntes.models.scenario1.Empresa;
import com.parcial.apuntes.services.scenario1.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ESCENARIO 1: Controlador REST para Empresa
 * 
 * @RestController: combina @Controller + @ResponseBody
 * @RequestMapping: define la ruta base para todos los métodos
 */
@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
    
    @Autowired
    private EmpresaService empresaService;
    
    /**
     * POST /api/empresas
     * Crear una nueva empresa
     */
    @PostMapping
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa empresa) {
        try {
            Empresa nuevaEmpresa = empresaService.crearEmpresa(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEmpresa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * GET /api/empresas
     * Obtener todas las empresas
     */
    @GetMapping
    public ResponseEntity<List<Empresa>> obtenerTodas() {
        List<Empresa> empresas = empresaService.obtenerTodas();
        return ResponseEntity.ok(empresas);
    }
    
    /**
     * GET /api/empresas/{id}
     * Obtener una empresa por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerPorId(@PathVariable Long id) {
        try {
            Empresa empresa = empresaService.obtenerEmpresa(id);
            return ResponseEntity.ok(empresa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * PUT /api/empresas/{id}
     * Actualizar una empresa
     */
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> actualizarEmpresa(
            @PathVariable Long id,
            @RequestBody Empresa empresa) {
        try {
            Empresa actualizada = empresaService.actualizarEmpresa(id, empresa);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * DELETE /api/empresas/{id}
     * Eliminar una empresa
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        try {
            empresaService.eliminarEmpresa(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /api/empresas/sector/{sector}
     * Obtener empresas de un sector
     */
    @GetMapping("/sector/{sector}")
    public ResponseEntity<List<Empresa>> obtenerPorSector(@PathVariable String sector) {
        try {
            List<Empresa> empresas = empresaService.obtenerPorSector(sector);
            return ResponseEntity.ok(empresas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
