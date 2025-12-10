# Sistema de Reservas de Hoteles - Microservicios

Sistema completo de reservas de hoteles con arquitectura de microservicios.

## 🏗️ Arquitectura

- **API Gateway** (Go/Fiber) - Puerto 3000
- **Users MS** (PHP/Laravel) - Puerto 8001
- **Hotels MS** (Java/Spring Boot) - Puerto 8002
- **Reservations MS** (C#/.NET) - Puerto 8003
- **Notifications MS** (Java/Spring Boot) - Puerto 8004
- **Frontend** (Angular 18) - Puerto 4200

## 🚀 Inicio Rápido

```bash
# 1. Clonar
git clone https://github.com/victorcanchari61-collab/MicroServicios.git
cd MicroServicios

# 2. Levantar todo
docker-compose up -d --build

# 3. Esperar 30 segundos e inicializar DB
docker exec -it users-service php artisan migrate

# 4. Acceder al frontend (opcional)
cd frontend-reservas-hoteles
npm install
npm start
```

Frontend: `http://localhost:4200`

## 👥 Usuario Admin

- Email: `admin@hotel.com`
- Password: `admin123`

## 📝 Comandos

```bash
docker-compose logs -f          # Ver logs
docker-compose down             # Detener
docker-compose restart          # Reiniciar
```

## 👨‍💻 Autor

Victor Canchari
