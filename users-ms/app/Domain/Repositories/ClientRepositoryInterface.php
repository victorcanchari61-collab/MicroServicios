<?php

namespace App\Domain\Repositories;

use App\Domain\Entities\Client;
use App\Domain\ValueObjects\ClientId;
use App\Domain\ValueObjects\Email;

interface ClientRepositoryInterface
{
    public function save(Client $client): void;
    
    public function findById(ClientId $id): ?Client;
    
    public function findByEmail(Email $email): ?Client;
    
    public function existsByEmail(Email $email): bool;
    
    public function delete(ClientId $id): void;
    
    public function all(): array;
}
