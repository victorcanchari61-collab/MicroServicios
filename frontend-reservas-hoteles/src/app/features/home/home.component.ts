import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  features = [
    {
      icon: '🏨',
      title: 'Hoteles de calidad',
      description: 'Amplia selección de hoteles verificados'
    },
    {
      icon: '💳',
      title: 'Reservas seguras',
      description: 'Sistema de pago 100% seguro'
    },
    {
      icon: '⭐',
      title: 'Mejor precio',
      description: 'Garantía del mejor precio disponible'
    },
    {
      icon: '📱',
      title: 'Soporte 24/7',
      description: 'Atención al cliente siempre disponible'
    }
  ];
}
