<?php

namespace App\Application\Auth\UseCase;

use App\Application\Auth\DTO\RegisterRequest;
use App\Application\Auth\DTO\RegisterResponse;
use App\Domain\Entities\User;
use App\Domain\Repositories\UserRepositoryInterface;
use App\Domain\Services\PasswordHasherInterface;
use App\Domain\Services\UuidGeneratorInterface;
use App\Domain\ValueObjects\Email;
use App\Domain\ValueObjects\UserId;
use InvalidArgumentException;

class RegisterUseCase
{
    public function __construct(
        private UserRepositoryInterface $userRepository,
        private PasswordHasherInterface $passwordHasher,
        private UuidGeneratorInterface $uuidGenerator
    ) {}

    public function execute(RegisterRequest $request): RegisterResponse
    {
        $email = new Email($request->email);

        // Check if user already exists (SRP: Repository handles this)
        if ($this->userRepository->existsByEmail($email)) {
            throw new InvalidArgumentException('Email already registered');
        }

        // Get role_id from database
        $role = \App\Models\Role::where('name', $request->role)->first();
        
        if (!$role) {
            throw new InvalidArgumentException('Invalid role. Must be: admin, manager, receptionist, or staff');
        }

        // Create user entity using abstractions (DIP)
        $user = new User(
            id: new UserId($this->uuidGenerator->generate()),
            name: $request->name,
            email: $email,
            password: $this->passwordHasher->hash($request->password),
            role: $role->name
        );

        // Save user (SRP: Repository handles persistence)
        $this->userRepository->save($user);

        return RegisterResponse::fromUser($user);
    }
}
