<?php

namespace App\Presentation\Http\Controllers;

use App\Application\Auth\DTO\RegisterClientRequest;
use App\Application\Auth\DTO\LoginClientRequest;
use App\Application\Auth\UseCase\RegisterClientUseCase;
use App\Application\Auth\UseCase\LoginClientUseCase;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Routing\Controller;

class ClientController extends Controller
{
    public function __construct(
        private RegisterClientUseCase $registerClientUseCase,
        private LoginClientUseCase $loginClientUseCase
    ) {}

    public function register(Request $request): JsonResponse
    {
        $validated = $request->validate([
            'first_name' => 'required|string|max:255',
            'last_name' => 'required|string|max:255',
            'email' => 'required|email|max:255',
            'password' => 'required|string|min:8',
            'phone' => 'nullable|string|max:20',
            'address' => 'nullable|string|max:500',
        ]);

        try {
            $dto = RegisterClientRequest::fromArray($validated);
            $response = $this->registerClientUseCase->execute($dto);

            return response()->json([
                'success' => true,
                'message' => 'Client registered successfully',
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
            $dto = LoginClientRequest::fromArray($validated);
            $response = $this->loginClientUseCase->execute($dto);

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
