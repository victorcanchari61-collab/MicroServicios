<?php

use App\Presentation\Http\Controllers\AuthController;
use App\Presentation\Http\Controllers\ClientController;
use Illuminate\Support\Facades\Route;

// Admin/Staff Authentication routes (ONLY admin can login to system)
Route::post('/admin/register', [AuthController::class, 'register']);
Route::post('/admin/login', [AuthController::class, 'login']);

// Client Authentication routes (customers who make reservations - public access)
Route::post('/clients/register', [ClientController::class, 'register']);
Route::post('/clients/login', [ClientController::class, 'login']);

// Protected routes (require authentication from API Gateway)
Route::middleware('auth.gateway')->group(function () {
    Route::get('/users/profile', function () {
        return response()->json([
            'user_id' => request()->header('X-User-ID'),
            'email' => request()->header('X-User-Email'),
            'role' => request()->header('X-User-Role'),
        ]);
    });
});
