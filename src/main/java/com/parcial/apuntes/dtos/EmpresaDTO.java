package com.parcial.apuntes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para Empresa
 * 
 * Los DTOs se usan para:
 * - Transferir datos entre controlador y cliente
 * - Separar la estructura DB de lo que ve el cliente
 * - Validar datos de entrada
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {
    private Long id;
    private String nombre;
    private String ciudad;
    private String sector;
    private Integer cantidadEmpleados;
}
