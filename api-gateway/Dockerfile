FROM golang:1.23-alpine AS builder

WORKDIR /app

# Copy go mod files and download dependencies (cached layer)
COPY go.mod go.sum ./
RUN go mod download

# Copy source and build
COPY . .
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o main ./cmd/gateway

FROM alpine:latest

# Install ca-certificates for HTTPS
RUN apk --no-cache add ca-certificates

WORKDIR /app

# Create non-root user
RUN addgroup -S app && adduser -S app -G app
USER app:app

# Copy binary from builder
COPY --from=builder /app/main .

EXPOSE 3000

# Use exec form for proper signal handling
CMD ["./main"]
