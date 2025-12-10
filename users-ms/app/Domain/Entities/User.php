<?php

namespace App\Domain\Entities;

use App\Domain\ValueObjects\Email;
use App\Domain\ValueObjects\UserId;

class User
{
    private UserId $id;
    private string $name;
    private Email $email;
    private string $password;
    private ?string $role;
    private ?\DateTimeImmutable $emailVerifiedAt;
    private \DateTimeImmutable $createdAt;
    private \DateTimeImmutable $updatedAt;

    public function __construct(
        UserId $id,
        string $name,
        Email $email,
        string $password,
        string $role = 'staff',
        ?\DateTimeImmutable $emailVerifiedAt = null,
        ?\DateTimeImmutable $createdAt = null,
        ?\DateTimeImmutable $updatedAt = null
    ) {
        $this->id = $id;
        $this->name = $name;
        $this->email = $email;
        $this->password = $password;
        $this->role = $role;
        $this->emailVerifiedAt = $emailVerifiedAt;
        $this->createdAt = $createdAt ?? new \DateTimeImmutable();
        $this->updatedAt = $updatedAt ?? new \DateTimeImmutable();
    }

    public function getId(): UserId
    {
        return $this->id;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getEmail(): Email
    {
        return $this->email;
    }

    public function getPassword(): string
    {
        return $this->password;
    }

    public function getRole(): ?string
    {
        return $this->role;
    }

    public function isEmailVerified(): bool
    {
        return $this->emailVerifiedAt !== null;
    }

    public function verifyEmail(): void
    {
        $this->emailVerifiedAt = new \DateTimeImmutable();
    }

    public function updateProfile(string $name, Email $email): void
    {
        $this->name = $name;
        $this->email = $email;
        $this->updatedAt = new \DateTimeImmutable();
    }

    public function changePassword(string $hashedPassword): void
    {
        $this->password = $hashedPassword;
        $this->updatedAt = new \DateTimeImmutable();
    }
}
