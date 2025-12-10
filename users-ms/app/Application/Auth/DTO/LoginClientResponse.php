<?php

namespace App\Application\Auth\DTO;

use App\Domain\Entities\Client;

class LoginClientResponse
{
    public function __construct(
        public readonly string $clientId,
        public readonly string $firstName,
        public readonly string $lastName,
        public readonly string $email,
        public readonly string $token
    ) {}

    public static function fromClientAndToken(Client $client, string $token): self
    {
        return new self(
            clientId: $client->getId()->value(),
            firstName: $client->getFirstName(),
            lastName: $client->getLastName(),
            email: $client->getEmail()->value(),
            token: $token
        );
    }

    public function toArray(): array
    {
        return [
            'client_id' => $this->clientId,
            'first_name' => $this->firstName,
            'last_name' => $this->lastName,
            'email' => $this->email,
            'token' => $this->token,
        ];
    }
}
