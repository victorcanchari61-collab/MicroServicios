<?php

namespace App\Domain\Entities;

use App\Domain\ValueObjects\Email;
use App\Domain\ValueObjects\ClientId;

class Client
{
    private ClientId $id;
    private string $firstName;
    private string $lastName;
    private Email $email;
    private string $password;
    private ?string $phone;
    private ?string $address;
    private ?\DateTimeImmutable $emailVerifiedAt;
    private \DateTimeImmutable $createdAt;
    private \DateTimeImmutable $updatedAt;

    public function __construct(
        ClientId $id,
        string $firstName,
        string $lastName,
        Email $email,
        string $password,
        ?string $phone = null,
        ?string $address = null,
        ?\DateTimeImmutable $emailVerifiedAt = null,
        ?\DateTimeImmutable $createdAt = null,
        ?\DateTimeImmutable $updatedAt = null
    ) {
        $this->id = $id;
        $this->firstName = $firstName;
        $this->lastName = $lastName;
        $this->email = $email;
        $this->password = $password;
        $this->phone = $phone;
        $this->address = $address;
        $this->emailVerifiedAt = $emailVerifiedAt;
        $this->createdAt = $createdAt ?? new \DateTimeImmutable();
        $this->updatedAt = $updatedAt ?? new \DateTimeImmutable();
    }

    public function getId(): ClientId
    {
        return $this->id;
    }

    public function getFirstName(): string
    {
        return $this->firstName;
    }

    public function getLastName(): string
    {
        return $this->lastName;
    }

    public function getFullName(): string
    {
        return "{$this->firstName} {$this->lastName}";
    }

    public function getEmail(): Email
    {
        return $this->email;
    }

    public function getPassword(): string
    {
        return $this->password;
    }

    public function getPhone(): ?string
    {
        return $this->phone;
    }

    public function getAddress(): ?string
    {
        return $this->address;
    }

    public function isEmailVerified(): bool
    {
        return $this->emailVerifiedAt !== null;
    }

    public function verifyEmail(): void
    {
        $this->emailVerifiedAt = new \DateTimeImmutable();
        $this->updatedAt = new \DateTimeImmutable();
    }

    public function updateProfile(string $firstName, string $lastName, Email $email, ?string $phone, ?string $address): void
    {
        $this->firstName = $firstName;
        $this->lastName = $lastName;
        $this->email = $email;
        $this->phone = $phone;
        $this->address = $address;
        $this->updatedAt = new \DateTimeImmutable();
    }

    public function changePassword(string $hashedPassword): void
    {
        $this->password = $hashedPassword;
        $this->updatedAt = new \DateTimeImmutable();
    }
}
