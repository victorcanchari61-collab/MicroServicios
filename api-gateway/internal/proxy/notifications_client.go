package proxy

import (
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/proxy"
)

type NotificationsClient struct {
	baseURL string
}

func NewNotificationsClient(baseURL string) *NotificationsClient {
	return &NotificationsClient{
		baseURL: baseURL,
	}
}

func (c *NotificationsClient) Forward(ctx *fiber.Ctx) error {
	// Keep the full path for Spring Boot (expects /api/notifications)
	path := ctx.Path()
	
	// Build target URL with query params
	url := c.baseURL + path
	if len(ctx.Request().URI().QueryString()) > 0 {
		url += "?" + string(ctx.Request().URI().QueryString())
	}
	
	if err := proxy.Do(ctx, url); err != nil {
		return ctx.Status(fiber.StatusBadGateway).JSON(fiber.Map{
			"error": "Notifications service unavailable",
		})
	}
	
	return nil
}
