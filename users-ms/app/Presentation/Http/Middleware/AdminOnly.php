<?php

namespace App\Presentation\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class AdminOnly
{
    public function handle(Request $request, Closure $next): Response
    {
        $user = $request->user();

        if (!$user) {
            return response()->json([
                'error' => 'Unauthorized',
                'message' => 'Authentication required'
            ], 401);
        }

        if ($user->role !== 'admin') {
            return response()->json([
                'error' => 'Forbidden',
                'message' => 'Only administrators can access this system'
            ], 403);
        }

        return $next($request);
    }
}
