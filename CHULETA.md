# CHULETA PARCIAL - SPRING BOOT + JPA (PRACTICA)

## 1) Plantilla general (siempre)

1. Crear entidades con relaciones y PK.
2. Crear repositories con metodos derivados y @Query.
3. Crear service con validaciones + @Transactional en escritura.
4. Crear controller REST CRUD + endpoints de consulta.

## 2) Estructura base por clase

- Entidad:
  - `@Entity`, `@Table`, `@Id`, `@GeneratedValue`
  - relaciones: `@ManyToOne`, `@OneToMany(mappedBy=...)`, `@ManyToMany`
- Repository:
  - `extends JpaRepository<Entidad, IdType>`
  - `findBy...`, `countBy...`, `existsBy...`
  - `@Query("...")` con `@Param`
- Service:
  - validar nulos/rangos/FK existente
  - `@Transactional` para create/update/delete
- Controller:
  - `@RestController`, `@RequestMapping`
  - `@PostMapping @GetMapping @PutMapping @DeleteMapping`

## 3) Checklist de examen

- [ ] Relacion correcta y lado propietario correcto
- [ ] `mappedBy` bien escrito
- [ ] Evitar ciclo JSON (`@JsonIgnore` o DTO)
- [ ] FK valida antes de guardar
- [ ] `@Transactional` en escritura
- [ ] Query probada (JPQL usa nombre de entidad/campos, no tabla)
- [ ] Respuestas HTTP correctas (200/201/204/400/404)

## 4) Recetas rapidas

### One-to-Many
- Hijo: `@ManyToOne @JoinColumn(name="padre_id")`
- Padre: `@OneToMany(mappedBy="padre")`

### Many-to-Many
- Lado propietario:
  - `@ManyToMany`
  - `@JoinTable(name="x_y", joinColumns=..., inverseJoinColumns=...)`
- Lado inverso:
  - `@ManyToMany(mappedBy="...")`

### Llave compuesta
- PK: `@Embeddable` + `implements Serializable`
- Entidad: `@EmbeddedId`
- Si hay relaciones: `@MapsId` o `insertable=false, updatable=false`

### Query tipica
- Por derivacion: `findByNombreContainingIgnoreCase(String nombre)`
- JPQL con join:
  - `SELECT h FROM Hijo h JOIN h.padre p WHERE p.id = :id`

## 5) Si te piden "haga la consulta X"

1. Crea metodo en repository (`@Query` o derivacion)
2. Llamalo desde service
3. Exponlo en endpoint GET con `@PathVariable` o `@RequestParam`
4. Devuelve lista/objeto y maneja vacios

## 6) Donde esta el codigo listo para copiar

- `src/main/java/com/parcial/apuntes/chuleta/ParcialChuleta.java`
