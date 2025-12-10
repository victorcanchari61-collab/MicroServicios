FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build

WORKDIR /src

# Copy csproj and restore dependencies (cached layer)
COPY reservations-ms/*.csproj .
RUN dotnet restore

# Copy source and publish
COPY reservations-ms/ .
RUN dotnet publish -c Release -o /app/publish --no-restore

FROM mcr.microsoft.com/dotnet/aspnet:8.0-alpine

WORKDIR /app

# Install ICU libraries for globalization
RUN apk add --no-cache icu-libs

# Set environment variables for globalization
ENV DOTNET_SYSTEM_GLOBALIZATION_INVARIANT=false

# Create non-root user
RUN addgroup -S dotnet && adduser -S dotnet -G dotnet
USER dotnet:dotnet

# Copy published app
COPY --from=build /app/publish .

EXPOSE 8080

# Use exec form for proper signal handling
ENTRYPOINT ["dotnet", "reservations-ms.dll"]
