<?php

namespace App\Application\Auth\DTO;

use App\Domain\Entities\User;

class RegisterResponse
{
    public function __construct(
        public readonly string $userId,
        public readonly string $name,
        public readonly string $email,
        public readonly string $role
    ) {}

    public static function fromUser(User $user): self
    {
        return new self(
            userId: $user->getId()->value(),
            name: $user->getName(),
            email: $user->getEmail()->value(),
            role: $user->getRole()
        );
    }

    public function toArray(): array
    {
        return [
            'id' => $this->userId,
            'name' => $this->name,
            'email' => $this->email,
            'role' => $this->role,
        ];
    }
}
