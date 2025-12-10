<?php

namespace App\Domain\ValueObjects;

use InvalidArgumentException;

class UserRole
{
    public const ADMIN = 'admin';
    public const MANAGER = 'manager';
    public const RECEPTIONIST = 'receptionist';
    public const STAFF = 'staff';

    private const VALID_ROLES = [
        self::ADMIN,
        self::MANAGER,
        self::RECEPTIONIST,
        self::STAFF,
    ];

    private string $value;

    public function __construct(string $value)
    {
        if (!in_array($value, self::VALID_ROLES, true)) {
            throw new InvalidArgumentException("Invalid user role: {$value}");
        }

        $this->value = $value;
    }

    public function value(): string
    {
        return $this->value;
    }

    public function isAdmin(): bool
    {
        return $this->value === self::ADMIN;
    }

    public function isManager(): bool
    {
        return $this->value === self::MANAGER;
    }

    public function equals(UserRole $other): bool
    {
        return $this->value === $other->value;
    }

    public function __toString(): string
    {
        return $this->value;
    }
}
