<?php

namespace App\Infrastructure\Repositories;

use App\Domain\Entities\Client as ClientEntity;
use App\Domain\Repositories\ClientRepositoryInterface;
use App\Domain\ValueObjects\ClientId;
use App\Domain\ValueObjects\Email;
use App\Models\Client as EloquentClient;

class EloquentClientRepository implements ClientRepositoryInterface
{
    public function save(ClientEntity $client): void
    {
        EloquentClient::updateOrCreate(
            ['id' => $client->getId()->value()],
            [
                'first_name' => $client->getFirstName(),
                'last_name' => $client->getLastName(),
                'email' => $client->getEmail()->value(),
                'password' => $client->getPassword(),
                'phone' => $client->getPhone(),
                'address' => $client->getAddress(),
                'email_verified_at' => $client->isEmailVerified() ? now() : null,
            ]
        );
    }

    public function findById(ClientId $id): ?ClientEntity
    {
        $eloquentClient = EloquentClient::find($id->value());
        
        return $eloquentClient ? $this->toDomain($eloquentClient) : null;
    }

    public function findByEmail(Email $email): ?ClientEntity
    {
        $eloquentClient = EloquentClient::where('email', $email->value())->first();
        
        return $eloquentClient ? $this->toDomain($eloquentClient) : null;
    }

    public function existsByEmail(Email $email): bool
    {
        return EloquentClient::where('email', $email->value())->exists();
    }

    public function delete(ClientId $id): void
    {
        EloquentClient::destroy($id->value());
    }

    public function all(): array
    {
        return EloquentClient::all()
            ->map(fn($client) => $this->toDomain($client))
            ->toArray();
    }

    private function toDomain(EloquentClient $eloquentClient): ClientEntity
    {
        return new ClientEntity(
            id: new ClientId($eloquentClient->id),
            firstName: $eloquentClient->first_name,
            lastName: $eloquentClient->last_name,
            email: new Email($eloquentClient->email),
            password: $eloquentClient->password,
            phone: $eloquentClient->phone,
            address: $eloquentClient->address,
            emailVerifiedAt: $eloquentClient->email_verified_at 
                ? new \DateTimeImmutable($eloquentClient->email_verified_at) 
                : null,
            createdAt: new \DateTimeImmutable($eloquentClient->created_at),
            updatedAt: new \DateTimeImmutable($eloquentClient->updated_at)
        );
    }
}
