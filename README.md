# Universidad API - Spring Boot

Sistema universitario con herencia e interfaces, sin persistencia (datos en memoria).

## Diagrama UML

![UML Universidad](image/uml%20niversidad.png)

## Datos precargados

### Estudiantes
| ID | Nombre         | Correo                           | Password | Código  |
|----|----------------|----------------------------------|----------|---------|
| 1  | Ana García     | ana.garcia@universidad.edu       | pass123  | EST001  |
| 2  | Luis Martínez  | luis.martinez@universidad.edu    | pass456  | EST002  |
| 3  | Sara López     | sara.lopez@universidad.edu       | pass789  | EST003  |

### Profesores
| ID | Nombre           | Correo                           | Password | Especialidad   |
|----|------------------|----------------------------------|----------|----------------|
| 1  | Dr. Carlos Ruiz  | carlos.ruiz@universidad.edu      | prof123  | Matemáticas    |
| 2  | Dra. Elena Vargas| elena.vargas@universidad.edu     | prof456  | Programación   |
| 3  | Mg. Pedro Sánchez| pedro.sanchez@universidad.edu    | prof789  | Física         |

### Administrativos
| ID | Nombre           | Correo                           | Password | Área                    |
|----|------------------|----------------------------------|----------|-------------------------|
| 1  | Martha Jiménez   | martha.jimenez@universidad.edu   | adm123   | Registro y Control      |
| 2  | Roberto Díaz     | roberto.diaz@universidad.edu     | adm456   | Bienestar Universitario |

---

## Endpoints

### Estudiantes `/api/estudiantes`

#### Listar todos
```
GET /api/estudiantes
```

#### Buscar por ID
```
GET /api/estudiantes/1
```

#### Buscar por código
```
GET /api/estudiantes/codigo/EST001
```

#### Crear estudiante
```
POST /api/estudiantes
Content-Type: application/json

{
  "nombre": "Carlos Perez",
  "correo": "carlos.perez@correo.com",
  "password": "clave123",
  "codigo": "EST010"
}
```

#### Crear con correo inválido (ejemplo de error)
```
POST /api/estudiantes
Content-Type: application/json

{
  "nombre": "Carlos Perez",
  "correo": "correo-sin-arroba",
  "password": "clave123",
  "codigo": "EST010"
}
```
Respuesta 400:
```json
{
  "success": false,
  "mensaje": "❌ Error de validación: El formato del correo 'correo-sin-arroba' no es válido. Debe tener el formato: usuario@dominio.com",
  "data": null
}
```

#### Actualizar estudiante
```
PUT /api/estudiantes/1
Content-Type: application/json

{
  "nombre": "Ana García Actualizada",
  "correo": "ana.garcia@universidad.edu",
  "password": "nuevaclave",
  "codigo": "EST001"
}
```

#### Eliminar estudiante
```
DELETE /api/estudiantes/1
```

#### Login estudiante
```
POST /api/estudiantes/login
Content-Type: application/json

{
  "correo": "ana.garcia@universidad.edu",
  "password": "pass123"
}
```

#### Notificar estudiante
```
POST /api/estudiantes/1/notificar
Content-Type: application/json

{
  "mensaje": "Tienes un examen mañana a las 8am."
}
```

---

### Profesores `/api/profesores`

#### Listar todos
```
GET /api/profesores
```

#### Crear profesor
```
POST /api/profesores
Content-Type: application/json

{
  "nombre": "Nuevo Profesor",
  "correo": "nuevo@universidad.edu",
  "password": "clave123",
  "especialidad": "Química"
}
```

#### Evaluar estudiante
```
POST /api/profesores/evaluar
Content-Type: application/json

{
  "profesorId": 1,
  "codigoEstudiante": "EST001",
  "nota": 4.5
}
```

#### Aprobar solicitud
```
POST /api/profesores/1/aprobar
Content-Type: application/json

{
  "codigoSolicitud": "SOL-2024-001"
}
```

#### Notificar profesor
```
POST /api/profesores/1/notificar
Content-Type: application/json

{
  "mensaje": "Reunión de docentes el viernes."
}
```

---

### Administrativos `/api/administrativos`

#### Listar todos
```
GET /api/administrativos
```

#### Crear administrativo
```
POST /api/administrativos
Content-Type: application/json

{
  "nombre": "Nuevo Admin",
  "correo": "admin.nuevo@universidad.edu",
  "password": "clave123",
  "area": "Tesorería"
}
```

#### Aprobar solicitud
```
POST /api/administrativos/1/aprobar
Content-Type: application/json

{
  "codigoSolicitud": "SOL-2024-099"
}
```

#### Notificar administrativo
```
POST /api/administrativos/1/notificar
Content-Type: application/json

{
  "mensaje": "Recordatorio: cierre de notas esta semana."
}
```

---


```

### Formato de éxito estándar
```json
{
  "success": true,
  "mensaje": "✅ Descripción del resultado",
  "data": { ... }
}
```
