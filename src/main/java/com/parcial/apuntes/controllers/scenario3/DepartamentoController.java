package com.parcial.apuntes.controllers.scenario3;

import com.parcial.apuntes.models.scenario3.Departamento;
import com.parcial.apuntes.services.scenario3.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ESCENARIO 3: Controlador REST para Departamento (Autorreferencial)
 */
@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {
    
    @Autowired
    private DepartamentoService departamentoService;
    
    /**
     * POST /api/departamentos
     * Crear un departamento
     * Body: { "nombre": "Desarrollo", "descripcion": "...", "idPadre": 1 }
     */
    @PostMapping
    public ResponseEntity<Departamento> crearDepartamento(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Long idPadre) {
        try {
            Departamento departamento = departamentoService
                .crearDepartamento(nombre, descripcion, idPadre);
            return ResponseEntity.status(HttpStatus.CREATED).body(departamento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * GET /api/departamentos/raices
     * Obtener todos los departamentos raíz (sin padre)
     */
    @GetMapping("/raices")
    public ResponseEntity<List<Departamento>> obtenerRaices() {
        List<Departamento> raices = departamentoService.obtenerDepartamentosRaiz();
        return ResponseEntity.ok(raices);
    }
    
    /**
     * GET /api/departamentos/{id}/subordinados
     * Obtener departamentos subordinados a uno específico
     */
    @GetMapping("/{id}/subordinados")
    public ResponseEntity<List<Departamento>> obtenerSubordinados(@PathVariable Long id) {
        try {
            List<Departamento> subordinados = departamentoService.obtenerSubordinados(id);
            return ResponseEntity.ok(subordinados);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * PUT /api/departamentos/{id}/padre/{idPadre}
     * Mover un departamento bajo otro padre
     */
    @PutMapping("/{id}/padre/{idPadre}")
    public ResponseEntity<String> moverDepartamento(
            @PathVariable Long id,
            @PathVariable Long idPadre) {
        try {
            departamentoService.moverDepartamento(id, idPadre);
            return ResponseEntity.ok("Departamento movido exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * PUT /api/departamentos/{id}/sin-padre
     * Hacer un departamento raíz (eliminar su padre)
     */
    @PutMapping("/{id}/sin-padre")
    public ResponseEntity<String> hacerRaiz(@PathVariable Long id) {
        try {
            departamentoService.moverDepartamento(id, null);
            return ResponseEntity.ok("Departamento ahora es raíz");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
