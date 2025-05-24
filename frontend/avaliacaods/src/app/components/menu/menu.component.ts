import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { Router } from '@angular/router';
import { UserService } from '../../services/user/user.service';
import { User } from '../../models/User';
import { ConfirmService } from '../../services/confirm/confirm.service';

@Component({
  selector: 'app-menu',
  imports: [MatIconModule, NavBarComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {

  user!: User;

  constructor(private router: Router){}

  goToAccount(){
    this.router.navigate(['/conta'])
  }

  goToWithdrawal(){
    this.router.navigate(['/saque'])
  }

  goToDeposit(){
    this.router.navigate(['/deposito'])
  }

  goToBankStatement(){
    this.router.navigate(['/extrato'])
  }

  logout(){
    this.router.navigate(['/login'])
  }
}
