<?php

namespace App\Application\Auth\DTO;

use App\Domain\Entities\User;

class LoginResponse
{
    public function __construct(
        public readonly string $userId,
        public readonly string $name,
        public readonly string $email,
        public readonly string $role,
        public readonly string $token
    ) {}

    public static function fromUserAndToken(User $user, string $token): self
    {
        return new self(
            userId: $user->getId()->value(),
            name: $user->getName(),
            email: $user->getEmail()->value(),
            role: $user->getRole(),
            token: $token
        );
    }

    public function toArray(): array
    {
        return [
            'user' => [
                'id' => $this->userId,
                'name' => $this->name,
                'email' => $this->email,
                'role' => $this->role,
            ],
            'token' => $this->token
        ];
    }
}
