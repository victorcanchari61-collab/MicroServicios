<?php

namespace App\Infrastructure\Services;

use App\Domain\Services\UuidGeneratorInterface;
use Illuminate\Support\Str;

class LaravelUuidGenerator implements UuidGeneratorInterface
{
    public function generate(): string
    {
        return (string) Str::uuid();
    }
}
