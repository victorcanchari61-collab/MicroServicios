<?php

namespace App\Providers;

use App\Application\Services\JWTServiceInterface;
use App\Domain\Repositories\UserRepositoryInterface;
use App\Domain\Services\PasswordHasherInterface;
use App\Domain\Services\UuidGeneratorInterface;
use App\Infrastructure\Repositories\EloquentUserRepository;
use App\Infrastructure\Services\FirebaseJWTService;
use App\Infrastructure\Services\LaravelPasswordHasher;
use App\Infrastructure\Services\LaravelUuidGenerator;
use Illuminate\Support\ServiceProvider;

class DomainServiceProvider extends ServiceProvider
{
    public function register(): void
    {
        // Bind repositories (DIP: Depend on abstractions)
        $this->app->bind(
            UserRepositoryInterface::class,
            EloquentUserRepository::class
        );

        $this->app->bind(
            \App\Domain\Repositories\ClientRepositoryInterface::class,
            \App\Infrastructure\Repositories\EloquentClientRepository::class
        );

        // Bind domain services (DIP: Depend on abstractions)
        $this->app->bind(
            PasswordHasherInterface::class,
            LaravelPasswordHasher::class
        );

        $this->app->bind(
            UuidGeneratorInterface::class,
            LaravelUuidGenerator::class
        );

        // Bind application services
        $this->app->bind(
            JWTServiceInterface::class,
            FirebaseJWTService::class
        );
    }

    public function boot(): void
    {
        //
    }
}
