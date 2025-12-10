<?php

namespace App\Application\Auth\UseCase;

use App\Application\Auth\DTO\LoginRequest;
use App\Application\Auth\DTO\LoginResponse;
use App\Application\Services\JWTServiceInterface;
use App\Domain\Repositories\UserRepositoryInterface;
use App\Domain\Services\PasswordHasherInterface;
use App\Domain\ValueObjects\Email;
use InvalidArgumentException;

class LoginUseCase
{
    public function __construct(
        private UserRepositoryInterface $userRepository,
        private JWTServiceInterface $jwtService,
        private PasswordHasherInterface $passwordHasher
    ) {}

    public function execute(LoginRequest $request): LoginResponse
    {
        $email = new Email($request->email);
        
        // Find user
        $user = $this->userRepository->findByEmail($email);
        
        if (!$user) {
            throw new InvalidArgumentException('Invalid credentials');
        }

        // Verify password using abstraction (DIP)
        if (!$this->passwordHasher->verify($request->password, $user->getPassword())) {
            throw new InvalidArgumentException('Invalid credentials');
        }

        // Only admin users can login
        if ($user->getRole() !== 'admin') {
            throw new InvalidArgumentException('Access denied. Only administrators can access this system');
        }

        // Generate JWT token
        $token = $this->jwtService->generateToken(
            userId: $user->getId()->value(),
            email: $user->getEmail()->value(),
            role: $user->getRole()
        );

        return LoginResponse::fromUserAndToken($user, $token);
    }
}
