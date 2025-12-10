<?php

namespace App\Domain\Services;

interface UuidGeneratorInterface
{
    public function generate(): string;
}
