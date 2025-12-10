<?php

namespace App\Infrastructure\Repositories;

use App\Domain\Entities\User as UserEntity;
use App\Domain\Repositories\UserRepositoryInterface;
use App\Domain\ValueObjects\Email;
use App\Domain\ValueObjects\UserId;
use App\Models\User as EloquentUser;

class EloquentUserRepository implements UserRepositoryInterface
{
    public function save(UserEntity $user): void
    {
        // Get role_id from role name
        $role = \App\Models\Role::where('name', $user->getRole())->first();
        
        if (!$role) {
            throw new \InvalidArgumentException('Invalid role');
        }

        EloquentUser::updateOrCreate(
            ['id' => $user->getId()->value()],
            [
                'name' => $user->getName(),
                'email' => $user->getEmail()->value(),
                'password' => $user->getPassword(),
                'role_id' => $role->id,
                'email_verified_at' => $user->isEmailVerified() ? now() : null,
            ]
        );
    }

    public function findById(UserId $id): ?UserEntity
    {
        $eloquentUser = EloquentUser::find($id->value());
        
        return $eloquentUser ? $this->toDomain($eloquentUser) : null;
    }

    public function findByEmail(Email $email): ?UserEntity
    {
        $eloquentUser = EloquentUser::where('email', $email->value())->first();
        
        return $eloquentUser ? $this->toDomain($eloquentUser) : null;
    }

    public function existsByEmail(Email $email): bool
    {
        return EloquentUser::where('email', $email->value())->exists();
    }

    public function delete(UserId $id): void
    {
        EloquentUser::destroy($id->value());
    }

    public function all(): array
    {
        return EloquentUser::all()
            ->map(fn($user) => $this->toDomain($user))
            ->toArray();
    }

    private function toDomain(EloquentUser $eloquentUser): UserEntity
    {
        // Load role relationship
        $eloquentUser->load('role');
        
        return new UserEntity(
            id: new UserId($eloquentUser->id),
            name: $eloquentUser->name,
            email: new Email($eloquentUser->email),
            password: $eloquentUser->password,
            role: $eloquentUser->role->name,
            emailVerifiedAt: $eloquentUser->email_verified_at 
                ? new \DateTimeImmutable($eloquentUser->email_verified_at) 
                : null,
            createdAt: new \DateTimeImmutable($eloquentUser->created_at),
            updatedAt: new \DateTimeImmutable($eloquentUser->updated_at)
        );
    }
}
