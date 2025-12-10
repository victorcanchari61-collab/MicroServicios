package validator

import (
	"errors"
	"time"

	"github.com/golang-jwt/jwt/v5"
)

type Claims struct {
	UserID string `json:"user_id"`
	Email  string `json:"email"`
	Role   string `json:"role"`
	jwt.RegisteredClaims
}

// ValidateJWT only validates the JWT token
// Token generation is handled by users-ms
func ValidateJWT(tokenString, secret string) (*Claims, error) {
	// Parse with custom claims
	token, err := jwt.ParseWithClaims(tokenString, &Claims{}, func(token *jwt.Token) (interface{}, error) {
		return []byte(secret), nil
	})

	if err != nil {
		// Try parsing as map claims for Laravel tokens
		mapToken, mapErr := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
			return []byte(secret), nil
		})
		
		if mapErr != nil {
			return nil, err
		}
		
		if mapClaims, ok := mapToken.Claims.(jwt.MapClaims); ok && mapToken.Valid {
			// Check expiration
			if exp, ok := mapClaims["exp"].(float64); ok {
				if time.Now().Unix() > int64(exp) {
					return nil, errors.New("token expired")
				}
			}
			
			// Convert to Claims struct
			claims := &Claims{
				UserID: getString(mapClaims, "user_id"),
				Email:  getString(mapClaims, "email"),
				Role:   getString(mapClaims, "role"),
			}
			return claims, nil
		}
		
		return nil, errors.New("invalid token")
	}

	if claims, ok := token.Claims.(*Claims); ok && token.Valid {
		// Check if token is expired
		if claims.ExpiresAt != nil && claims.ExpiresAt.Time.Before(time.Now()) {
			return nil, errors.New("token expired")
		}
		return claims, nil
	}

	return nil, errors.New("invalid token")
}

func getString(m jwt.MapClaims, key string) string {
	if val, ok := m[key].(string); ok {
		return val
	}
	return ""
}
