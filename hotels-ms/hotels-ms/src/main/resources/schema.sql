-- Tabla de hoteles (ya existe)
CREATE TABLE IF NOT EXISTS hotels (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    address VARCHAR(500),
    city VARCHAR(100),
    country VARCHAR(100),
    stars INTEGER CHECK (stars >= 1 AND stars <= 5),
    price_per_night DECIMAL(10, 2) NOT NULL,
    total_rooms INTEGER NOT NULL,
    available_rooms INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Nueva tabla de habitaciones
CREATE TABLE IF NOT EXISTS rooms (
    id VARCHAR(255) PRIMARY KEY,
    hotel_id VARCHAR(255) NOT NULL,
    room_number VARCHAR(50) NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity >= 1),
    price_per_night DECIMAL(10, 2) NOT NULL CHECK (price_per_night > 0),
    description TEXT,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE,
    UNIQUE (hotel_id, room_number)
);

-- Índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_rooms_hotel_id ON rooms(hotel_id);
CREATE INDEX IF NOT EXISTS idx_rooms_is_available ON rooms(is_available);
CREATE INDEX IF NOT EXISTS idx_rooms_hotel_available ON rooms(hotel_id, is_available);
