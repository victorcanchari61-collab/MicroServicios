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

Aquí están los scripts SQL para insertar datos de prueba después de levantar el proyecto:

1. Insertar Usuario Administrador (PostgreSQL - Users MS)
docker exec -it users-db psql -U postgres -d users_db -c "
INSERT INTO users (id, email, name, password, role, created_at, updated_at) 
VALUES (
    'a1b2c3d4-e5f6-7890-abcd-ef1234567890',
    'admin@hotel.com',
    'Administrador',
    '\$2y\$12\$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'admin',
    NOW(),
    NOW()
) ON CONFLICT (id) DO NOTHING;
"
2. Insertar Hoteles (PostgreSQL - Hotels MS)
docker exec -it hotels-db psql -U postgres -d hotels_db -c "
INSERT INTO hotels (id, name, description, address, city, country, stars, price_per_night, total_rooms, available_rooms, created_at, updated_at)
VALUES 
    ('f5f4f299-1833-4a4f-bef1-d121c1af4e9d', 'Hotel Paradise', 'Hermoso hotel frente al mar', 'Av. Playa 123', 'Lima', 'Peru', 5, 150.00, 20, 20, NOW(), NOW()),
    ('4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', 'Hotel Central', 'Hotel céntrico', 'Calle Principal 456', 'Lima', 'Peru', 4, 100.00, 15, 15, NOW(), NOW()),
    ('c0a5438a-5ba9-4389-8102-a0bb2819256f', 'Hotel Cielo', 'Hotel boutique', 'Av. Mirador 789', 'Cusco', 'Peru', 4, 120.00, 10, 10, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;
"
3. Insertar Habitaciones (PostgreSQL - Hotels MS)
docker exec -it hotels-db psql -U postgres -d hotels_db -c "
INSERT INTO rooms (id, hotel_id, room_number, room_type, capacity, price_per_night, description, is_available, created_at, updated_at)
VALUES 
    -- Hotel Paradise
    ('b7332308-4e64-458c-bf63-78369ca5f069', 'f5f4f299-1833-4a4f-bef1-d121c1af4e9d', '101', 'SINGLE', 1, 80.00, 'Habitación individual', true, NOW(), NOW()),
    ('e6f4873e-024d-43d7-9494-1d6d303f462f', 'f5f4f299-1833-4a4f-bef1-d121c1af4e9d', '102', 'DOUBLE', 2, 120.00, 'Habitación doble', true, NOW(), NOW()),
    ('91dfce3e-1367-425c-b657-cb2b37142f65', 'f5f4f299-1833-4a4f-bef1-d121c1af4e9d', '201', 'SUITE', 4, 250.00, 'Suite de lujo', true, NOW(), NOW()),
    -- Hotel Central
    ('674bfd17-fa9e-40de-a99b-0c432a0945ba', '4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', '101', 'SINGLE', 1, 70.00, 'Habitación económica', true, NOW(), NOW()),
    ('3bfdd779-1f12-4db4-b937-d5c15540d255', '4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', '102', 'DOUBLE', 2, 100.00, 'Habitación doble', true, NOW(), NOW()),
    ('84f85023-7fc9-43f2-9b7e-b49bcf781ea1', '4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', '201', 'SUITE', 3, 180.00, 'Suite familiar', true, NOW(), NOW()),
    -- Hotel Cielo
    ('a1a1a1a1-b2b2-c3c3-d4d4-e5e5e5e5e5e5', 'c0a5438a-5ba9-4389-8102-a0bb2819256f', '301', 'SINGLE', 1, 90.00, 'Vista a la montaña', true, NOW(), NOW()),
    ('b2b2b2b2-c3c3-d4d4-e5e5-f6f6f6f6f6f6', 'c0a5438a-5ba9-4389-8102-a0bb2819256f', '302', 'DOUBLE', 2, 130.00, 'Habitación premium', true, NOW(), NOW()),
    ('c3c3c3c3-d4d4-e5e5-f6f6-a7a7a7a7a7a7', 'c0a5438a-5ba9-4389-8102-a0bb2819256f', '401', 'DELUXE', 3, 200.00, 'Habitación deluxe', true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;
"
Script Todo-en-Uno
# Ejecutar todo de una vez
docker exec -it users-db psql -U postgres -d users_db -c "INSERT INTO users (id, email, name, password, role, created_at, updated_at) VALUES ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'admin@hotel.com', 'Administrador', '\$2y\$12\$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin', NOW(), NOW()) ON CONFLICT (id) DO NOTHING;"

docker exec -it hotels-db psql -U postgres -d hotels_db -c "INSERT INTO hotels (id, name, description, address, city, country, stars, price_per_night, total_rooms, available_rooms, created_at, updated_at) VALUES ('f5f4f299-1833-4a4f-bef1-d121c1af4e9d', 'Hotel Paradise', 'Hermoso hotel frente al mar', 'Av. Playa 123', 'Lima', 'Peru', 5, 150.00, 20, 20, NOW(), NOW()), ('4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', 'Hotel Central', 'Hotel céntrico', 'Calle Principal 456', 'Lima', 'Peru', 4, 100.00, 15, 15, NOW(), NOW()), ('c0a5438a-5ba9-4389-8102-a0bb2819256f', 'Hotel Cielo', 'Hotel boutique', 'Av. Mirador 789', 'Cusco', 'Peru', 4, 120.00, 10, 10, NOW(), NOW()) ON CONFLICT (id) DO NOTHING;"

docker exec -it hotels-db psql -U postgres -d hotels_db -c "INSERT INTO rooms (id, hotel_id, room_number, room_type, capacity, price_per_night, description, is_available, created_at, updated_at) VALUES ('b7332308-4e64-458c-bf63-78369ca5f069', 'f5f4f299-1833-4a4f-bef1-d121c1af4e9d', '101', 'SINGLE', 1, 80.00, 'Habitación individual', true, NOW(), NOW()), ('e6f4873e-024d-43d7-9494-1d6d303f462f', 'f5f4f299-1833-4a4f-bef1-d121c1af4e9d', '102', 'DOUBLE', 2, 120.00, 'Habitación doble', true, NOW(), NOW()), ('91dfce3e-1367-425c-b657-cb2b37142f65', 'f5f4f299-1833-4a4f-bef1-d121c1af4e9d', '201', 'SUITE', 4, 250.00, 'Suite de lujo', true, NOW(), NOW()), ('674bfd17-fa9e-40de-a99b-0c432a0945ba', '4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', '101', 'SINGLE', 1, 70.00, 'Habitación económica', true, NOW(), NOW()), ('3bfdd779-1f12-4db4-b937-d5c15540d255', '4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', '102', 'DOUBLE', 2, 100.00, 'Habitación doble', true, NOW(), NOW()), ('84f85023-7fc9-43f2-9b7e-b49bcf781ea1', '4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', '201', 'SUITE', 3, 180.00, 'Suite familiar', true, NOW(), NOW()), ('a1a1a1a1-b2b2-c3c3-d4d4-e5e5e5e5e5e5', 'c0a5438a-5ba9-4389-8102-a0bb2819256f', '301', 'SINGLE', 1, 90.00, 'Vista a la montaña', true, NOW(), NOW()), ('b2b2b2b2-c3c3-d4d4-e5e5-f6f6f6f6f6f6', 'c0a5438a-5ba9-4389-8102-a0bb2819256f', '302', 'DOUBLE', 2, 130.00, 'Habitación premium', true, NOW(), NOW()), ('c3c3c3c3-d4d4-e5e5-f6f6-a7a7a7a7a7a7', 'c0a5438a-5ba9-4389-8102-a0bb2819256f', '401', 'DELUXE', 3, 200.00, 'Habitación deluxe', true, NOW(), NOW()) ON CONFLICT (id) DO NOTHING;"

echo "✅ Datos insertados correctamente"
Credenciales del admin:

Email: admin@hotel.com
Password: admin123