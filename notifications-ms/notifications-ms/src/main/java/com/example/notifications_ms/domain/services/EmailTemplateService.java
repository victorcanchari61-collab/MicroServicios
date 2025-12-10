package com.example.notifications_ms.domain.services;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class EmailTemplateService {
    
    public String getReservationConfirmationTemplate(Map<String, String> variables) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f8f9fa; padding: 30px; border-radius: 0 0 10px 10px; }
                    .details { background: white; padding: 20px; border-radius: 8px; margin: 20px 0; border-left: 4px solid #667eea; }
                    .detail-row { display: flex; justify-content: space-between; padding: 10px 0; border-bottom: 1px solid #eee; }
                    .detail-label { font-weight: bold; color: #667eea; }
                    .total { font-size: 24px; color: #667eea; font-weight: bold; text-align: center; margin: 20px 0; }
                    .footer { text-align: center; color: #7f8c8d; font-size: 12px; margin-top: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🎉 ¡Reserva Confirmada!</h1>
                    </div>
                    <div class="content">
                        <p>Estimado/a <strong>%s</strong>,</p>
                        <p>Su reserva ha sido confirmada exitosamente. A continuación los detalles:</p>
                        
                        <div class="details">
                            <div class="detail-row">
                                <span class="detail-label">🏨 Hotel:</span>
                                <span>%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">📅 Check-in:</span>
                                <span>%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">📅 Check-out:</span>
                                <span>%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">🔖 Código de Reserva:</span>
                                <span><strong>%s</strong></span>
                            </div>
                        </div>
                        
                        <div class="total">
                            💰 Total: %s
                        </div>
                        
                        <p>Por favor, presente este correo al momento del check-in.</p>
                        <p>¡Esperamos que disfrute su estadía!</p>
                        
                        <div class="footer">
                            <p>Este es un correo automático, por favor no responder.</p>
                            <p>Sistema de Reservas de Hoteles © 2025</p>
                        </div>
                    </div>
                </div>
            </body>
            </html>
            """,
            variables.getOrDefault("clientName", "Cliente"),
            variables.getOrDefault("hotelName", "Hotel"),
            variables.getOrDefault("checkInDate", ""),
            variables.getOrDefault("checkOutDate", ""),
            variables.getOrDefault("reservationId", ""),
            variables.getOrDefault("totalPrice", "S/ 0.00")
        );
    }

    public String getReservationCancellationTemplate(Map<String, String> variables) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"></head>
            <body style="font-family: Arial, sans-serif; padding: 20px;">
                <h2 style="color: #e74c3c;">Cancelación de Reserva</h2>
                <p>Estimado/a <strong>%s</strong>,</p>
                <p>Su reserva ha sido cancelada.</p>
                <p>Si tiene alguna pregunta, no dude en contactarnos.</p>
                <p>Gracias.</p>
            </body>
            </html>
            """,
            variables.getOrDefault("clientName", "Cliente")
        );
    }

    public String getWelcomeTemplate(Map<String, String> variables) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"></head>
            <body style="font-family: Arial, sans-serif; padding: 20px;">
                <h2 style="color: #27ae60;">¡Bienvenido!</h2>
                <p>Hola <strong>%s</strong>,</p>
                <p>Gracias por registrarte en nuestro sistema de reservas.</p>
                <p>Estamos encantados de tenerte con nosotros.</p>
                <p>¡Felices viajes!</p>
            </body>
            </html>
            """,
            variables.getOrDefault("name", "Usuario")
        );
    }
}
