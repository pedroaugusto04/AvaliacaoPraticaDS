import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { NavBarComponent } from '../nav-bar/nav-bar.component';

@Component({
  selector: 'app-menu',
  imports: [MatIconModule, NavBarComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {

}
