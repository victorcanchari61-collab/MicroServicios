package main

import (
	"api-gateway/internal/config"
	"api-gateway/internal/middleware"
	"api-gateway/internal/router"
	"log"

	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/recover"
)

func main() {
	// Load configuration
	cfg := config.Load()

	// Create Fiber app
	app := fiber.New(fiber.Config{
		AppName:      "API Gateway",
		ServerHeader: "API Gateway",
	})

	// Setup global middleware
	app.Use(recover.New())
	app.Use(middleware.Logger())
	app.Use(middleware.CORS())

	// Setup routes
	router.Setup(app, cfg)

	// Start server
	log.Printf("🚀 API Gateway running on port %s", cfg.Port)
	log.Fatal(app.Listen(":" + cfg.Port))
}
