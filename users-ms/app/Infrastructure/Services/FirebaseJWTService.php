<?php

namespace App\Infrastructure\Services;

use App\Application\Services\JWTServiceInterface;
use Firebase\JWT\JWT;
use Firebase\JWT\Key;

class FirebaseJWTService implements JWTServiceInterface
{
    private string $secret;
    private string $algorithm = 'HS256';
    private int $expirationTime = 86400; // 24 hours

    public function __construct()
    {
        $this->secret = config('app.jwt_secret', env('JWT_SECRET', 'your-secret-key'));
    }

    public function generateToken(string $userId, string $email, ?string $role): string
    {
        $payload = [
            'user_id' => $userId,
            'email' => $email,
            'role' => $role ?? 'user',
            'iat' => time(),
            'exp' => time() + $this->expirationTime
        ];

        return JWT::encode($payload, $this->secret, $this->algorithm);
    }

    public function validateToken(string $token): ?array
    {
        try {
            $decoded = JWT::decode($token, new Key($this->secret, $this->algorithm));
            return (array) $decoded;
        } catch (\Exception $e) {
            return null;
        }
    }

    public function refreshToken(string $token): string
    {
        $payload = $this->validateToken($token);
        
        if (!$payload) {
            throw new \InvalidArgumentException('Invalid token');
        }

        return $this->generateToken(
            $payload['user_id'],
            $payload['email'],
            $payload['role']
        );
    }
}
