package config

import "os"

type Config struct {
	Port              string
	UsersServiceURL   string
	HotelsServiceURL  string
	ReservationsURL   string
	NotificationsURL  string
	JWTSecret         string
}

func Load() *Config {
	return &Config{
		Port:              getEnv("PORT", "3000"),
		UsersServiceURL:   getEnv("USERS_SERVICE_URL", "http://users-service:8000"),
		HotelsServiceURL:  getEnv("HOTELS_SERVICE_URL", "http://hotels-service:8080"),
		ReservationsURL:   getEnv("RESERVATIONS_SERVICE_URL", "http://reservations-service:8080"),
		NotificationsURL:  getEnv("NOTIFICATIONS_SERVICE_URL", "http://notifications-service:8080"),
		JWTSecret:         getEnv("JWT_SECRET", "your-secret-key-change-in-production"),
	}
}

func getEnv(key, defaultValue string) string {
	if value := os.Getenv(key); value != "" {
		return value
	}
	return defaultValue
}
