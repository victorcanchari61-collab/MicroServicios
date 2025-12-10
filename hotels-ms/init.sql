-- Datos iniciales para Hotels MS

-- Hoteles de ejemplo
INSERT INTO hotels (id, name, description, address, city, country, stars, price_per_night, total_rooms, available_rooms, created_at, updated_at)
VALUES 
    ('f5f4f299-1833-4a4f-bef1-d121c1af4e9d', 'Hotel Paradise', 'Hermoso hotel frente al mar con todas las comodidades', 'Av. Playa 123', 'Lima', 'Peru', 5, 150.00, 20, 20, NOW(), NOW()),
    ('4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', 'Hotel Central', 'Hotel céntrico con excelente ubicación', 'Calle Principal 456', 'Lima', 'Peru', 4, 100.00, 15, 15, NOW(), NOW()),
    ('c0a5438a-5ba9-4389-8102-a0bb2819256f', 'Hotel Cielo', 'Hotel boutique con vistas panorámicas', 'Av. Mirador 789', 'Cusco', 'Peru', 4, 120.00, 10, 10, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- Habitaciones para Hotel Paradise
INSERT INTO rooms (id, hotel_id, room_number, room_type, capacity, price_per_night, description, is_available, created_at, updated_at)
VALUES 
    ('b7332308-4e64-458c-bf63-78369ca5f069', 'f5f4f299-1833-4a4f-bef1-d121c1af4e9d', '101', 'SINGLE', 1, 80.00, 'Habitación individual con vista al mar', true, NOW(), NOW()),
    ('e6f4873e-024d-43d7-9494-1d6d303f462f', 'f5f4f299-1833-4a4f-bef1-d121c1af4e9d', '102', 'DOUBLE', 2, 120.00, 'Habitación doble con balcón', true, NOW(), NOW()),
    ('91dfce3e-1367-425c-b657-cb2b37142f65', 'f5f4f299-1833-4a4f-bef1-d121c1af4e9d', '201', 'SUITE', 4, 250.00, 'Suite de lujo con jacuzzi', true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- Habitaciones para Hotel Central
INSERT INTO rooms (id, hotel_id, room_number, room_type, capacity, price_per_night, description, is_available, created_at, updated_at)
VALUES 
    ('674bfd17-fa9e-40de-a99b-0c432a0945ba', '4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', '101', 'SINGLE', 1, 70.00, 'Habitación económica', true, NOW(), NOW()),
    ('3bfdd779-1f12-4db4-b937-d5c15540d255', '4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', '102', 'DOUBLE', 2, 100.00, 'Habitación doble estándar', true, NOW(), NOW()),
    ('84f85023-7fc9-43f2-9b7e-b49bcf781ea1', '4f88a4fd-7c0a-4027-8471-4a33bb6be9aa', '201', 'SUITE', 3, 180.00, 'Suite familiar', true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- Habitaciones para Hotel Cielo
INSERT INTO rooms (id, hotel_id, room_number, room_type, capacity, price_per_night, description, is_available, created_at, updated_at)
VALUES 
    ('a1a1a1a1-b2b2-c3c3-d4d4-e5e5e5e5e5e5', 'c0a5438a-5ba9-4389-8102-a0bb2819256f', '301', 'SINGLE', 1, 90.00, 'Habitación con vista a la montaña', true, NOW(), NOW()),
    ('b2b2b2b2-c3c3-d4d4-e5e5-f6f6f6f6f6f6', 'c0a5438a-5ba9-4389-8102-a0bb2819256f', '302', 'DOUBLE', 2, 130.00, 'Habitación doble premium', true, NOW(), NOW()),
    ('c3c3c3c3-d4d4-e5e5-f6f6-a7a7a7a7a7a7', 'c0a5438a-5ba9-4389-8102-a0bb2819256f', '401', 'DELUXE', 3, 200.00, 'Habitación deluxe con terraza', true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

SELECT 'Datos iniciales de Hotels MS cargados correctamente' as status;
