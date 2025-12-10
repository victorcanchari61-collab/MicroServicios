<?php

namespace App\Domain\Repositories;

use App\Domain\Entities\User;
use App\Domain\ValueObjects\Email;
use App\Domain\ValueObjects\UserId;

interface UserRepositoryInterface
{
    public function save(User $user): void;
    
    public function findById(UserId $id): ?User;
    
    public function findByEmail(Email $email): ?User;
    
    public function existsByEmail(Email $email): bool;
    
    public function delete(UserId $id): void;
    
    public function all(): array;
}
