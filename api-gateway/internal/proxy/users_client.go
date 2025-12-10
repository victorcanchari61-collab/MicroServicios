package proxy

import (
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/proxy"
)

type UsersClient struct {
	baseURL string
}

func NewUsersClient(baseURL string) *UsersClient {
	return &UsersClient{
		baseURL: baseURL,
	}
}

func (c *UsersClient) Forward(ctx *fiber.Ctx) error {
	// Remove /api prefix from the path
	path := ctx.Path()
	if len(path) >= 4 && path[:4] == "/api" {
		path = path[4:]
	}
	
	// Prepend /api for Laravel routes
	path = "/api" + path
	
	// Build target URL with query params
	url := c.baseURL + path
	if len(ctx.Request().URI().QueryString()) > 0 {
		url += "?" + string(ctx.Request().URI().QueryString())
	}
	
	// Ensure Accept header is set for Laravel
	if ctx.Get("Accept") == "" {
		ctx.Set("Accept", "application/json")
	}
	
	if err := proxy.Do(ctx, url); err != nil {
		return ctx.Status(fiber.StatusBadGateway).JSON(fiber.Map{
			"error": "Users service unavailable",
		})
	}
	
	return nil
}
