<?php

namespace App\Application\Services;

interface JWTServiceInterface
{
    public function generateToken(string $userId, string $email, ?string $role): string;
    
    public function validateToken(string $token): ?array;
    
    public function refreshToken(string $token): string;
}
