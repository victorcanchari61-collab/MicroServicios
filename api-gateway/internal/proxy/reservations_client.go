package proxy

import (
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/proxy"
)

type ReservationsClient struct {
	baseURL string
}

func NewReservationsClient(baseURL string) *ReservationsClient {
	return &ReservationsClient{
		baseURL: baseURL,
	}
}

func (c *ReservationsClient) Forward(ctx *fiber.Ctx) error {
	// Keep the full path for .NET (expects /api/reservations)
	path := ctx.Path()
	
	// Build target URL with query params
	url := c.baseURL + path
	if len(ctx.Request().URI().QueryString()) > 0 {
		url += "?" + string(ctx.Request().URI().QueryString())
	}
	
	if err := proxy.Do(ctx, url); err != nil {
		return ctx.Status(fiber.StatusBadGateway).JSON(fiber.Map{
			"error": "Reservations service unavailable",
		})
	}
	
	return nil
}
