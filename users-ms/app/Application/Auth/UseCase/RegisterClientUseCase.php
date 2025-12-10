<?php

namespace App\Application\Auth\UseCase;

use App\Application\Auth\DTO\RegisterClientRequest;
use App\Application\Auth\DTO\RegisterResponse;
use App\Domain\Entities\Client;
use App\Domain\Repositories\ClientRepositoryInterface;
use App\Domain\Services\PasswordHasherInterface;
use App\Domain\Services\UuidGeneratorInterface;
use App\Domain\ValueObjects\ClientId;
use App\Domain\ValueObjects\Email;
use InvalidArgumentException;

class RegisterClientUseCase
{
    public function __construct(
        private ClientRepositoryInterface $clientRepository,
        private PasswordHasherInterface $passwordHasher,
        private UuidGeneratorInterface $uuidGenerator
    ) {}

    public function execute(RegisterClientRequest $request): RegisterResponse
    {
        $email = new Email($request->email);

        // Check if client already exists
        if ($this->clientRepository->existsByEmail($email)) {
            throw new InvalidArgumentException('Email already registered');
        }

        // Create client entity
        $client = new Client(
            id: new ClientId($this->uuidGenerator->generate()),
            firstName: $request->firstName,
            lastName: $request->lastName,
            email: $email,
            password: $this->passwordHasher->hash($request->password),
            phone: $request->phone,
            address: $request->address
        );

        // Save client
        $this->clientRepository->save($client);

        return new RegisterResponse(
            userId: $client->getId()->value(),
            name: $client->getFullName(),
            email: $client->getEmail()->value(),
            role: 'client'
        );
    }
}
