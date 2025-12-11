docker-compose up -d --build
docker exec -it users-service php artisan migrate

# Sistema de Reservas de Hoteles - Microservicios

Sistema completo de reservas de hoteles con arquitectura de microservicios.

## 🏗️ Arquitectura del Sistema

```
┌─────────────────────────────────────────────────────────────────┐
│                     Frontend (Angular 18)                        │
│                      Puerto: 4200                                │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                   API Gateway (Go/Fiber)                         │
│                      Puerto: 3000                                │
│              • Enrutamiento                                      │
│              • Autenticación JWT                                 │
└─────┬──────────┬──────────┬──────────┬─────────────────────────┘
      │          │          │          │
      ▼          ▼          ▼          ▼
┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────────┐
│ Users MS │ │Hotels MS │ │Reserv. MS│ │Notifications │
│  Laravel │ │  Spring  │ │   .NET   │ │    Spring    │
│  :8001   │ │  :8002   │ │  :8003   │ │    :8004     │
└────┬─────┘ └────┬─────┘ └────┬─────┘ └──────┬───────┘
     │            │            │               │
     ▼            ▼            ▼               ▼
┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────────┐
│PostgreSQL│ │PostgreSQL│ │SQL Server│ │   MongoDB    │
│  :5440   │ │  :5441   │ │  :1440   │ │    :27020    │
└──────────┘ └──────────┘ └──────────┘ └──────────────┘
```

### Tecnologías por Microservicio

- **API Gateway** (Go/Fiber) - Puerto 3000
- **Users MS** (PHP/Laravel) - Puerto 8001 → PostgreSQL
- **Hotels MS** (Java/Spring Boot) - Puerto 8002 → PostgreSQL
- **Reservations MS** (C#/.NET) - Puerto 8003 → SQL Server
- **Notifications MS** (Java/Spring Boot) - Puerto 8004 → MongoDB
- **Frontend** (Angular 18) - Puerto 4200

## 🚀 Inicio Rápido

```bash
# 1. Clonar
git clone https://github.com/victorcanchari61-collab/MicroServicios.git
cd MicroServicios

# 2. Levantar todo (primera vez tarda 5-10 minutos)
docker-compose up -d --build

# 3. Esperar a que todos los servicios estén listos (1-2 minutos)
docker-compose logs -f

# 4. Ejecutar migraciones de Laravel
docker exec -it users-service php artisan migrate --force

# 5. Acceder al sistema
# API Gateway: http://localhost:3000
# Frontend: http://localhost:4000
```

## 👥 Datos Iniciales

**IMPORTANTE:** El sistema inicia con bases de datos vacías. Debes:

1. **Registrar un usuario administrador** desde el frontend o API
2. **Crear hoteles** desde el panel de administrador
3. **Agregar habitaciones** a cada hotel

O ejecutar este script para datos de prueba:
```bash
# Crear usuario admin
docker exec -it users-service php artisan tinker
# Luego en tinker ejecutar el código para crear admin
```

## 📝 Comandos

```bash
docker-compose logs -f          # Ver logs
docker-compose down             # Detener
docker-compose restart          # Reiniciar
```

## 👨‍💻 Autor

Victor Canchari
