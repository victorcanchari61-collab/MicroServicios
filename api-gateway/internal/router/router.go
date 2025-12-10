package router

import (
	"api-gateway/internal/config"
	"api-gateway/internal/middleware"
	"api-gateway/internal/services"

	"github.com/gofiber/fiber/v2"
)

func Setup(app *fiber.App, cfg *config.Config) {
	// Initialize reverse proxy service
	reverseProxy := services.NewReverseProxy(cfg)

	// Health check
	app.Get("/health", func(c *fiber.Ctx) error {
		return c.JSON(fiber.Map{
			"status":  "ok",
			"service": "api-gateway",
			"version": "1.0.0",
		})
	})

	// API routes
	api := app.Group("/api")

	// Public routes (no auth required)
	setupPublicRoutes(api, reverseProxy)

	// Protected routes (auth required)
	protected := api.Group("", middleware.JWTAuth(cfg))
	setupProtectedRoutes(protected, reverseProxy)
}

func setupPublicRoutes(router fiber.Router, proxy *services.ReverseProxy) {
	// Users service - Authentication endpoints (Admin)
	router.Post("/admin/register", proxy.ForwardToUsers)
	router.Post("/admin/login", proxy.ForwardToUsers)
	
	// Users service - Authentication endpoints (Clients)
	router.Post("/clients/register", proxy.ForwardToUsers)
	router.Post("/clients/login", proxy.ForwardToUsers)
	
	// Legacy routes (backward compatibility)
	router.Post("/users/register", proxy.ForwardToUsers)
	router.Post("/users/login", proxy.ForwardToUsers)
	router.Post("/users/refresh-token", proxy.ForwardToUsers)
	router.Post("/users/forgot-password", proxy.ForwardToUsers)
	router.Post("/users/reset-password", proxy.ForwardToUsers)
	
	// Hotels service - Public endpoints
	router.Get("/hotels", proxy.ForwardToHotels)
	router.Get("/hotels/:id", proxy.ForwardToHotels)
}

func setupProtectedRoutes(router fiber.Router, proxy *services.ReverseProxy) {
	// Users service - Protected endpoints
	router.Get("/users/profile", proxy.ForwardToUsers)
	router.Put("/users/profile", proxy.ForwardToUsers)
	router.Post("/users/logout", proxy.ForwardToUsers)

	// Hotels service - Admin endpoints
	router.Post("/hotels", proxy.ForwardToHotels)
	router.Put("/hotels/:id", proxy.ForwardToHotels)
	router.Delete("/hotels/:id", proxy.ForwardToHotels)

	// Rooms service - Admin endpoints
	router.Get("/hotels/:hotelId/rooms", proxy.ForwardToHotels)
	router.Post("/hotels/:hotelId/rooms", proxy.ForwardToHotels)
	router.Get("/rooms/:id", proxy.ForwardToHotels)
	router.Put("/rooms/:id", proxy.ForwardToHotels)
	router.Delete("/rooms/:id", proxy.ForwardToHotels)
	router.Post("/rooms/:id/hold", proxy.ForwardToHotels)
	router.Post("/rooms/:id/release", proxy.ForwardToHotels)

	// Reservations service - All endpoints require auth
	router.Post("/reservations", proxy.ForwardToReservations)
	router.Get("/reservations/client/:clientId", proxy.ForwardToReservations)
	router.Get("/reservations", proxy.ForwardToReservations)
	router.Get("/reservations/:id", proxy.ForwardToReservations)
	router.Put("/reservations/:id/cancel", proxy.ForwardToReservations)
	router.Put("/reservations/:id", proxy.ForwardToReservations)
	router.Delete("/reservations/:id", proxy.ForwardToReservations)

	// Notifications service - All endpoints require auth
	router.Post("/notifications", proxy.ForwardToNotifications)
	router.Post("/notifications/send", proxy.ForwardToNotifications)
	router.Get("/notifications", proxy.ForwardToNotifications)
	router.Get("/notifications/:id", proxy.ForwardToNotifications)
}
