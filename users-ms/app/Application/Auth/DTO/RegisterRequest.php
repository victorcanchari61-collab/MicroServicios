<?php

namespace App\Application\Auth\DTO;

class RegisterRequest
{
    public function __construct(
        public readonly string $name,
        public readonly string $email,
        public readonly string $password,
        public readonly string $role = 'staff'
    ) {}

    public static function fromArray(array $data): self
    {
        return new self(
            name: $data['name'],
            email: $data['email'],
            password: $data['password'],
            role: $data['role'] ?? 'staff'
        );
    }
}
