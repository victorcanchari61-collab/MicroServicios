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

# 2. Levantar todo (primera vez tarda más)
docker-compose up -d --build

# 3. Esperar 30 segundos e inicializar DB
docker exec -it users-service php artisan migrate

# 4. Acceder al sistema
# Frontend: http://localhost:4200
# API Gateway: http://localhost:3000
```

## 👥 Datos Iniciales

El sistema viene con datos de prueba precargados:

**Usuario Administrador:**
- Email: `admin@hotel.com`
- Password: `admin123`

**Hoteles de ejemplo:**
- Hotel Paradise (5 estrellas) - 3 habitaciones
- Hotel Central (4 estrellas) - 3 habitaciones  
- Hotel Cielo (4 estrellas) - 3 habitaciones

**Total: 3 hoteles con 9 habitaciones listas para reservar**

## 📝 Comandos

```bash
docker-compose logs -f          # Ver logs
docker-compose down             # Detener
docker-compose restart          # Reiniciar
```

## 👨‍💻 Autor

Victor Canchari
