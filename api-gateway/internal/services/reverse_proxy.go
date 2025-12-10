package services

import (
	"api-gateway/internal/config"
	"api-gateway/internal/proxy"

	"github.com/gofiber/fiber/v2"
)

type ReverseProxy struct {
	usersClient         *proxy.UsersClient
	hotelsClient        *proxy.HotelsClient
	reservationsClient  *proxy.ReservationsClient
	notificationsClient *proxy.NotificationsClient
}

func NewReverseProxy(cfg *config.Config) *ReverseProxy {
	return &ReverseProxy{
		usersClient:         proxy.NewUsersClient(cfg.UsersServiceURL),
		hotelsClient:        proxy.NewHotelsClient(cfg.HotelsServiceURL),
		reservationsClient:  proxy.NewReservationsClient(cfg.ReservationsURL),
		notificationsClient: proxy.NewNotificationsClient(cfg.NotificationsURL),
	}
}

func (rp *ReverseProxy) ForwardToUsers(c *fiber.Ctx) error {
	return rp.usersClient.Forward(c)
}

func (rp *ReverseProxy) ForwardToHotels(c *fiber.Ctx) error {
	return rp.hotelsClient.Forward(c)
}

func (rp *ReverseProxy) ForwardToReservations(c *fiber.Ctx) error {
	return rp.reservationsClient.Forward(c)
}

func (rp *ReverseProxy) ForwardToNotifications(c *fiber.Ctx) error {
	return rp.notificationsClient.Forward(c)
}
