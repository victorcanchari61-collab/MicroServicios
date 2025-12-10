package utils

import "github.com/gofiber/fiber/v2"

type ErrorResponse struct {
	Error   string `json:"error"`
	Message string `json:"message,omitempty"`
}

type SuccessResponse struct {
	Success bool        `json:"success"`
	Data    interface{} `json:"data,omitempty"`
	Message string      `json:"message,omitempty"`
}

func SendError(c *fiber.Ctx, status int, err string, message ...string) error {
	response := ErrorResponse{
		Error: err,
	}
	
	if len(message) > 0 {
		response.Message = message[0]
	}
	
	return c.Status(status).JSON(response)
}

func SendSuccess(c *fiber.Ctx, data interface{}, message ...string) error {
	response := SuccessResponse{
		Success: true,
		Data:    data,
	}
	
	if len(message) > 0 {
		response.Message = message[0]
	}
	
	return c.JSON(response)
}
