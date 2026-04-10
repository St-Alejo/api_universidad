package com.universidad.controller;

import com.universidad.dto.ApiResponse;
import com.universidad.dto.EstudianteRequest;
import com.universidad.dto.LoginRequest;
import com.universidad.model.Estudiante;
import com.universidad.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    // GET /api/estudiantes
    @GetMapping
    public ResponseEntity<ApiResponse<List<Estudiante>>> listar() {
        List<Estudiante> lista = estudianteService.listar();
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Se encontraron " + lista.size() + " estudiante(s).", lista));
    }

    // GET /api/estudiantes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Estudiante>> buscarPorId(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.buscarPorId(id);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Estudiante encontrado.", estudiante));
    }

    // GET /api/estudiantes/codigo/{codigo}
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ApiResponse<Estudiante>> buscarPorCodigo(@PathVariable String codigo) {
        Estudiante estudiante = estudianteService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Estudiante encontrado con código " + codigo + ".", estudiante));
    }

    // POST /api/estudiantes
    @PostMapping
    public ResponseEntity<ApiResponse<Estudiante>> crear(@RequestBody EstudianteRequest req) {
        Estudiante creado = estudianteService.crear(
            req.getNombre(), req.getCorreo(), req.getPassword(), req.getCodigo());
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.ok("✅ Estudiante '" + creado.getNombre() + "' creado exitosamente con código " + creado.getCodigo() + ".", creado));
    }

    // PUT /api/estudiantes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Estudiante>> actualizar(
            @PathVariable Long id, @RequestBody EstudianteRequest req) {
        Estudiante actualizado = estudianteService.actualizar(
            id, req.getNombre(), req.getCorreo(), req.getPassword(), req.getCodigo());
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Estudiante actualizado exitosamente.", actualizado));
    }

    // DELETE /api/estudiantes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Estudiante con id " + id + " eliminado exitosamente.", null));
    }

    // POST /api/estudiantes/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@RequestBody LoginRequest req) {
        boolean exito = estudianteService.login(req.getCorreo(), req.getPassword());
        if (exito) {
            return ResponseEntity.ok(ApiResponse.ok(
                "✅ Inicio de sesión exitoso. Bienvenido.",
                Map.of("autenticado", true, "rol", "ESTUDIANTE")));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ApiResponse.error("❌ Correo o contraseña incorrectos. Por favor verifica tus datos."));
    }

    // POST /api/estudiantes/{id}/notificar
    @PostMapping("/{id}/notificar")
    public ResponseEntity<ApiResponse<String>> notificar(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String mensaje = body.get("mensaje");
        String resultado = estudianteService.notificar(id, mensaje);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Notificación enviada.", resultado));
    }
}
