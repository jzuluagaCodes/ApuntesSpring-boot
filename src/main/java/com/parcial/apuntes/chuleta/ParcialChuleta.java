package com.parcial.apuntes.chuleta;

/*
 * CHULETA RAPIDA - TODO EN UNA SOLA CLASE
 *
 * NOTA: Este archivo es para copiar/pegar rapido en examen.
 * Los bloques estan separados por nombres para busqueda eficiente.
 */
public class ParcialChuleta {

    // ================================================================
    // [BLOQUE 01] ENTITY BASE
    // ================================================================
    /*
    @Entity
    @Table(name = "entidad")
    @Data
    public class Entidad {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entidad")
        @SequenceGenerator(name = "seq_entidad", initialValue = 1, allocationSize = 1)
        private Long id;

        @Column(nullable = false)
        private String nombre;
    }
    */

    // ================================================================
    // [BLOQUE 02] RELACION ONE-TO-MANY / MANY-TO-ONE
    // ================================================================
    /*
    // PADRE
    @Entity
    public class Empresa {
        @Id @GeneratedValue
        private Long id;

        private String nombre;

        @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Empleado> empleados;
    }

    // HIJO (lado propietario de la FK)
    @Entity
    public class Empleado {
        @Id @GeneratedValue
        private Long id;

        private String nombre;
        private Double salario;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "empresa_id", nullable = false)
        @JsonIgnore
        private Empresa empresa;
    }
    */

    // ================================================================
    // [BLOQUE 03] RELACION MANY-TO-MANY
    // ================================================================
    /*
    // Lado propietario
    @Entity
    public class Estudiante {
        @Id @GeneratedValue
        private Long id;

        @ManyToMany
        @JoinTable(
            name = "matricula",
            joinColumns = @JoinColumn(name = "estudiante_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
        )
        private List<Curso> cursos;
    }

    // Lado inverso
    @Entity
    public class Curso {
        @Id @GeneratedValue
        private Long id;

        @ManyToMany(mappedBy = "cursos")
        @JsonIgnore
        private List<Estudiante> estudiantes;
    }
    */

    // ================================================================
    // [BLOQUE 04] AUTORREFERENCIAL
    // ================================================================
    /*
    @Entity
    public class Departamento {
        @Id @GeneratedValue
        private Long id;

        private String nombre;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "departamento_padre_id")
        private Departamento padre;

        @OneToMany(mappedBy = "padre", cascade = CascadeType.ALL)
        private List<Departamento> hijos;
    }
    */

    // ================================================================
    // [BLOQUE 05] LLAVE COMPUESTA (EMBEDDABLE + EMBEDDEDID)
    // ================================================================
    /*
    @Embeddable
    @Data
    public class ProductoTiendaPK implements Serializable {
        private static final long serialVersionUID = 1L;

        @Column(name = "producto_id")
        private Long productoId;

        @Column(name = "tienda_id")
        private Long tiendaId;
    }

    @Entity
    public class ProductoTienda {
        @EmbeddedId
        private ProductoTiendaPK id;

        @ManyToOne
        @JoinColumn(name = "producto_id", insertable = false, updatable = false)
        private Producto producto;

        @ManyToOne
        @JoinColumn(name = "tienda_id", insertable = false, updatable = false)
        private Tienda tienda;

        private Integer stock;
        private Double precio;
    }
    */

    // ================================================================
    // [BLOQUE 06] REPOSITORY BASE
    // ================================================================
    /*
    @Repository
    public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

        // derivacion
        List<Empleado> findByNombreContainingIgnoreCase(String nombre);
        List<Empleado> findByEmpresaId(Long empresaId);
        long countByEmpresaId(Long empresaId);
        boolean existsByNombre(String nombre);

        // JPQL
        @Query("SELECT e FROM Empleado e JOIN e.empresa emp WHERE emp.id = :empresaId AND e.salario > :min")
        List<Empleado> buscarPorEmpresaYSalario(
            @Param("empresaId") Long empresaId,
            @Param("min") Double min
        );

        // Nativa
        @Query(value = "SELECT * FROM empleado WHERE salario BETWEEN :min AND :max", nativeQuery = true)
        List<Empleado> buscarRangoNativo(@Param("min") Double min, @Param("max") Double max);

        // Update masivo
        @Modifying
        @Transactional
        @Query("UPDATE Empleado e SET e.salario = e.salario * 1.1 WHERE e.empresa.id = :empresaId")
        int aumentarSalario(@Param("empresaId") Long empresaId);
    }
    */

    // ================================================================
    // [BLOQUE 07] SERVICE BASE
    // ================================================================
    /*
    @Service
    public class EmpleadoService {

        @Autowired
        private EmpleadoRepository empleadoRepository;

        @Autowired
        private EmpresaRepository empresaRepository;

        @Transactional
        public Empleado crear(Empleado emp, Long empresaId) {
            if (emp.getNombre() == null || emp.getNombre().isBlank()) {
                throw new IllegalArgumentException("nombre obligatorio");
            }
            if (emp.getSalario() == null || emp.getSalario() <= 0) {
                throw new IllegalArgumentException("salario invalido");
            }

            Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("empresa no existe"));

            emp.setId(null);
            emp.setEmpresa(empresa);
            return empleadoRepository.save(emp);
        }

        public Empleado obtener(Long id) {
            return empleadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no encontrado"));
        }

        @Transactional
        public Empleado actualizar(Long id, Empleado cambios) {
            Empleado actual = obtener(id);
            if (cambios.getNombre() != null) actual.setNombre(cambios.getNombre());
            if (cambios.getSalario() != null && cambios.getSalario() > 0) actual.setSalario(cambios.getSalario());
            return empleadoRepository.save(actual);
        }

        @Transactional
        public void eliminar(Long id) {
            Empleado actual = obtener(id);
            empleadoRepository.delete(actual);
        }
    }
    */

    // ================================================================
    // [BLOQUE 08] CONTROLLER BASE CRUD + CONSULTA
    // ================================================================
    /*
    @RestController
    @RequestMapping("/api/empleados")
    public class EmpleadoController {

        @Autowired
        private EmpleadoService service;

        @PostMapping("/empresa/{empresaId}")
        public ResponseEntity<?> crear(@PathVariable Long empresaId, @RequestBody Empleado body) {
            try {
                return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(body, empresaId));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> obtener(@PathVariable Long id) {
            try {
                return ResponseEntity.ok(service.obtener(id));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Empleado body) {
            try {
                return ResponseEntity.ok(service.actualizar(id, body));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminar(@PathVariable Long id) {
            try {
                service.eliminar(id);
                return ResponseEntity.noContent().build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }

        @GetMapping("/buscar")
        public ResponseEntity<?> buscar(@RequestParam Long empresaId, @RequestParam Double min) {
            return ResponseEntity.ok(service.buscarEmpresaSalario(empresaId, min));
        }
    }
    */

    // ================================================================
    // [BLOQUE 09] CONSULTAS RAPIDAS (SOLO METODOS)
    // ================================================================
    /*
    // Si piden "listar hijos por padre"
    @Query("SELECT h FROM Hijo h WHERE h.padre.id = :id")
    List<Hijo> hijosPorPadre(@Param("id") Long id);

    // Si piden "contar por estado"
    long countByEstado(String estado);

    // Si piden "existe por codigo"
    boolean existsByCodigo(String codigo);

    // Si piden "top salario"
    @Query("SELECT e FROM Empleado e WHERE e.empresa.id = :empresaId ORDER BY e.salario DESC")
    List<Empleado> topSalarios(@Param("empresaId") Long empresaId, Pageable pageable);

    // Uso: repo.topSalarios(id, PageRequest.of(0, 1));
    */

    // ================================================================
    // [BLOQUE 10] CURL RAPIDO (COPIAR)
    // ================================================================
    /*
    # crear
    curl -X POST http://localhost:8080/api/empleados/empresa/1 \
      -H "Content-Type: application/json" \
      -d '{"nombre":"Ana","salario":5000}'

    # obtener
    curl http://localhost:8080/api/empleados/1

    # actualizar
    curl -X PUT http://localhost:8080/api/empleados/1 \
      -H "Content-Type: application/json" \
      -d '{"salario":6500}'

    # borrar
    curl -X DELETE http://localhost:8080/api/empleados/1

    # consulta
    curl "http://localhost:8080/api/empleados/buscar?empresaId=1&min=3000"
    */

    // ================================================================
    // [BLOQUE 11] application.properties minimo
    // ================================================================
    /*
    spring.application.name=parcial

    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=

    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console

    spring.jpa.hibernate.ddl-auto=create-drop
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    */

    // ================================================================
    // [BLOQUE 12] ERRORES TIPICOS (ultra corto)
    // ================================================================
    /*
    1) StackOverflow / ciclo JSON
       -> usar @JsonIgnore o DTO.

    2) Constraint FK
       -> validar padre existe antes de guardar hijo.

    3) No guarda cambios
       -> falto @Transactional en service de escritura.

    4) @Query falla
       -> JPQL usa nombre de clase/campo, no nombre tabla/columna.

    5) PK compuesta no funciona
       -> @Embeddable + Serializable + equals/hashCode (Lombok @Data ayuda).
    */
}
