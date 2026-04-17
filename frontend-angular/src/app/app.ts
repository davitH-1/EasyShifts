import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './layout/header/header.component';
import { MatDivider } from '@angular/material/list';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, MatDivider],
  template: `
    <app-header></app-header>

    <mat-divider></mat-divider>

    <div class="page-container">
      <router-outlet></router-outlet>
    </div>
  `,
})
export class App {}
