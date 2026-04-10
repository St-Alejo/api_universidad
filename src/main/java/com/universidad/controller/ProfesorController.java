package com.universidad.controller;

import com.universidad.dto.ApiResponse;
import com.universidad.dto.EvaluarRequest;
import com.universidad.dto.ProfesorRequest;
import com.universidad.model.Profesor;
import com.universidad.service.ProfesorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    private final ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    // GET /api/profesores
    @GetMapping
    public ResponseEntity<ApiResponse<List<Profesor>>> listar() {
        List<Profesor> lista = profesorService.listar();
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Se encontraron " + lista.size() + " profesor(es).", lista));
    }

    // GET /api/profesores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Profesor>> buscarPorId(@PathVariable Long id) {
        Profesor profesor = profesorService.buscarPorId(id);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Profesor encontrado.", profesor));
    }

    // POST /api/profesores
    @PostMapping
    public ResponseEntity<ApiResponse<Profesor>> crear(@RequestBody ProfesorRequest req) {
        Profesor creado = profesorService.crear(
            req.getNombre(), req.getCorreo(), req.getPassword(), req.getEspecialidad());
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.ok("✅ Profesor '" + creado.getNombre() + "' creado exitosamente.", creado));
    }

    // PUT /api/profesores/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Profesor>> actualizar(
            @PathVariable Long id, @RequestBody ProfesorRequest req) {
        Profesor actualizado = profesorService.actualizar(
            id, req.getNombre(), req.getCorreo(), req.getPassword(), req.getEspecialidad());
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Profesor actualizado exitosamente.", actualizado));
    }

    // DELETE /api/profesores/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        profesorService.eliminar(id);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Profesor con id " + id + " eliminado exitosamente.", null));
    }

    // POST /api/profesores/evaluar
    @PostMapping("/evaluar")
    public ResponseEntity<ApiResponse<String>> evaluar(@RequestBody EvaluarRequest req) {
        String resultado = profesorService.evaluar(
            req.getProfesorId(), req.getCodigoEstudiante(), req.getNota());
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Evaluación registrada.", resultado));
    }

    // POST /api/profesores/{id}/aprobar
    @PostMapping("/{id}/aprobar")
    public ResponseEntity<ApiResponse<String>> aprobar(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String codigoSolicitud = body.get("codigoSolicitud");
        String resultado = profesorService.aprobarSolicitud(id, codigoSolicitud);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Solicitud procesada.", resultado));
    }

    // POST /api/profesores/{id}/notificar
    @PostMapping("/{id}/notificar")
    public ResponseEntity<ApiResponse<String>> notificar(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String mensaje = body.get("mensaje");
        String resultado = profesorService.notificar(id, mensaje);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Notificación enviada.", resultado));
    }
}
