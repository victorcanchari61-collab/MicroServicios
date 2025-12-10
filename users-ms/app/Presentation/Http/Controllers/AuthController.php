<?php

namespace App\Presentation\Http\Controllers;

use App\Application\Auth\DTO\LoginRequest;
use App\Application\Auth\DTO\RegisterRequest;
use App\Application\Auth\UseCase\LoginUseCase;
use App\Application\Auth\UseCase\RegisterUseCase;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Routing\Controller;

class AuthController extends Controller
{
    public function __construct(
        private RegisterUseCase $registerUseCase,
        private LoginUseCase $loginUseCase
    ) {}

    public function register(Request $request): JsonResponse
    {
        $validated = $request->validate([
            'name' => 'required|string|max:255',
            'email' => 'required|email|max:255',
            'password' => 'required|string|min:8',
            'role' => 'nullable|string|in:admin,manager,receptionist,staff',
        ]);

        try {
            $dto = RegisterRequest::fromArray($validated);
            $response = $this->registerUseCase->execute($dto);

            return response()->json([
                'success' => true,
                'message' => 'User registered successfully',
                'data' => $response->toArray()
            ], 201);
        } catch (\InvalidArgumentException $e) {
            return response()->json([
                'success' => false,
                'error' => $e->getMessage()
            ], 400);
        }
    }

    public function login(Request $request): JsonResponse
    {
        $validated = $request->validate([
            'email' => 'required|email',
            'password' => 'required|string',
        ]);

        try {
            $dto = LoginRequest::fromArray($validated);
            $response = $this->loginUseCase->execute($dto);

            return response()->json([
                'success' => true,
                'message' => 'Login successful',
                'data' => $response->toArray()
            ]);
        } catch (\InvalidArgumentException $e) {
            return response()->json([
                'success' => false,
                'error' => $e->getMessage()
            ], 401);
        }
    }
}
