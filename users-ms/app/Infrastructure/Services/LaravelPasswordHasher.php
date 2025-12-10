<?php

namespace App\Infrastructure\Services;

use App\Domain\Services\PasswordHasherInterface;
use Illuminate\Support\Facades\Hash;

class LaravelPasswordHasher implements PasswordHasherInterface
{
    public function hash(string $password): string
    {
        return Hash::make($password);
    }

    public function verify(string $password, string $hash): bool
    {
        return Hash::check($password, $hash);
    }
}
