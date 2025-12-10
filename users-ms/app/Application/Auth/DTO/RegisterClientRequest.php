<?php

namespace App\Application\Auth\DTO;

class RegisterClientRequest
{
    public function __construct(
        public readonly string $firstName,
        public readonly string $lastName,
        public readonly string $email,
        public readonly string $password,
        public readonly ?string $phone = null,
        public readonly ?string $address = null
    ) {}

    public static function fromArray(array $data): self
    {
        return new self(
            firstName: $data['first_name'],
            lastName: $data['last_name'],
            email: $data['email'],
            password: $data['password'],
            phone: $data['phone'] ?? null,
            address: $data['address'] ?? null
        );
    }
}
