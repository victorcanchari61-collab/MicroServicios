<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;

class DisableRedirects
{
    public function handle(Request $request, Closure $next)
    {
        // Disable trailing slash redirects for API routes
        if ($request->is('api/*')) {
            $request->server->set('REQUEST_URI', rtrim($request->server->get('REQUEST_URI'), '/'));
        }
        
        return $next($request);
    }
}
