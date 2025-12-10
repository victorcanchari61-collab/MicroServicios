<?php

namespace App\Application\Auth\UseCase;

use App\Application\Auth\DTO\LoginClientRequest;
use App\Application\Auth\DTO\LoginClientResponse;
use App\Application\Services\JWTServiceInterface;
use App\Domain\Repositories\ClientRepositoryInterface;
use App\Domain\Services\PasswordHasherInterface;
use App\Domain\ValueObjects\Email;
use InvalidArgumentException;

class LoginClientUseCase
{
    public function __construct(
        private ClientRepositoryInterface $clientRepository,
        private JWTServiceInterface $jwtService,
        private PasswordHasherInterface $passwordHasher
    ) {}

    public function execute(LoginClientRequest $request): LoginClientResponse
    {
        $email = new Email($request->email);
        
        // Find client
        $client = $this->clientRepository->findByEmail($email);
        
        if (!$client) {
            throw new InvalidArgumentException('Invalid credentials');
        }

        // Verify password
        if (!$this->passwordHasher->verify($request->password, $client->getPassword())) {
            throw new InvalidArgumentException('Invalid credentials');
        }

        // Generate JWT token for client (role: client)
        $token = $this->jwtService->generateToken(
            userId: $client->getId()->value(),
            email: $client->getEmail()->value(),
            role: 'client'
        );

        return LoginClientResponse::fromClientAndToken($client, $token);
    }
}
