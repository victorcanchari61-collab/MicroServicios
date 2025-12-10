package middleware

import (
	"api-gateway/internal/config"
	"api-gateway/internal/validator"
	"strings"

	"github.com/gofiber/fiber/v2"
)

func JWTAuth(cfg *config.Config) fiber.Handler {
	return func(c *fiber.Ctx) error {
		// Get Authorization header
		authHeader := c.Get("Authorization")
		if authHeader == "" {
			return c.Status(fiber.StatusUnauthorized).JSON(fiber.Map{
				"error": "Missing authorization header",
			})
		}

		// Extract token
		parts := strings.Split(authHeader, " ")
		if len(parts) != 2 || parts[0] != "Bearer" {
			return c.Status(fiber.StatusUnauthorized).JSON(fiber.Map{
				"error": "Invalid authorization header format",
			})
		}

		token := parts[1]

		// Validate token
		claims, err := validator.ValidateJWT(token, cfg.JWTSecret)
		if err != nil {
			return c.Status(fiber.StatusUnauthorized).JSON(fiber.Map{
				"error": "Invalid or expired token",
			})
		}

		// Store user info in context and forward to services
		c.Locals("user_id", claims.UserID)
		c.Locals("email", claims.Email)
		c.Locals("role", claims.Role)
		
		// Add user info to headers for downstream services
		c.Request().Header.Set("X-User-ID", claims.UserID)
		c.Request().Header.Set("X-User-Email", claims.Email)
		c.Request().Header.Set("X-User-Role", claims.Role)

		return c.Next()
	}
}
