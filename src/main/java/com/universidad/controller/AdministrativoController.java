package com.universidad.controller;

import com.universidad.dto.AdministrativoRequest;
import com.universidad.dto.ApiResponse;
import com.universidad.model.Administrativo;
import com.universidad.service.AdministrativoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/administrativos")
public class AdministrativoController {

    private final AdministrativoService administrativoService;

    public AdministrativoController(AdministrativoService administrativoService) {
        this.administrativoService = administrativoService;
    }

    // GET /api/administrativos
    @GetMapping
    public ResponseEntity<ApiResponse<List<Administrativo>>> listar() {
        List<Administrativo> lista = administrativoService.listar();
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Se encontraron " + lista.size() + " administrativo(s).", lista));
    }

    // GET /api/administrativos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Administrativo>> buscarPorId(@PathVariable Long id) {
        Administrativo admin = administrativoService.buscarPorId(id);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Administrativo encontrado.", admin));
    }

    // POST /api/administrativos
    @PostMapping
    public ResponseEntity<ApiResponse<Administrativo>> crear(@RequestBody AdministrativoRequest req) {
        Administrativo creado = administrativoService.crear(
            req.getNombre(), req.getCorreo(), req.getPassword(), req.getArea());
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.ok("✅ Administrativo '" + creado.getNombre() + "' creado exitosamente.", creado));
    }

    // PUT /api/administrativos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Administrativo>> actualizar(
            @PathVariable Long id, @RequestBody AdministrativoRequest req) {
        Administrativo actualizado = administrativoService.actualizar(
            id, req.getNombre(), req.getCorreo(), req.getPassword(), req.getArea());
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Administrativo actualizado exitosamente.", actualizado));
    }

    // DELETE /api/administrativos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        administrativoService.eliminar(id);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Administrativo con id " + id + " eliminado exitosamente.", null));
    }

    // POST /api/administrativos/{id}/aprobar
    @PostMapping("/{id}/aprobar")
    public ResponseEntity<ApiResponse<String>> aprobar(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String codigoSolicitud = body.get("codigoSolicitud");
        String resultado = administrativoService.aprobarSolicitud(id, codigoSolicitud);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Solicitud aprobada.", resultado));
    }

    // POST /api/administrativos/{id}/notificar
    @PostMapping("/{id}/notificar")
    public ResponseEntity<ApiResponse<String>> notificar(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String mensaje = body.get("mensaje");
        String resultado = administrativoService.notificar(id, mensaje);
        return ResponseEntity.ok(
            ApiResponse.ok("✅ Notificación enviada.", resultado));
    }
}
