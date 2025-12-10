-- Datos iniciales para Users MS

-- Usuario Administrador
INSERT INTO users (id, email, name, password, role, created_at, updated_at) 
VALUES (
    'a1b2c3d4-e5f6-7890-abcd-ef1234567890',
    'admin@hotel.com',
    'Administrador',
    '$2y$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', -- password: admin123
    'admin',
    NOW(),
    NOW()
) ON CONFLICT (id) DO NOTHING;

-- Cliente de prueba
INSERT INTO clients (id, user_id, first_name, last_name, phone, address, created_at, updated_at)
VALUES (
    'b7e0bfec-3c3d-4b52-8a16-a6eee670d65d',
    'a1b2c3d4-e5f6-7890-abcd-ef1234567890',
    'Victor',
    'Canchari',
    '+51987654321',
    'Lima, Peru',
    NOW(),
    NOW()
) ON CONFLICT (id) DO NOTHING;

SELECT 'Datos iniciales de Users MS cargados correctamente' as status;
