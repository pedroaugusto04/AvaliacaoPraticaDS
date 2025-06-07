import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { Router } from '@angular/router';
import { UserService } from '../../services/user/user.service';
import { User } from '../../models/User';
import { ConfirmService } from '../../services/confirm/confirm.service';
import { AuthenticationService } from '../../services/authentication/authentication.service';

@Component({
  selector: 'app-menu',
  imports: [MatIconModule, NavBarComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {

  user!: User;

  constructor(private router: Router, private confirmService: ConfirmService, private authenticationService: AuthenticationService ){}

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

  goToTransference() {
    this.router.navigate(['/transferencia'])
  }

  logout(){
    this.authenticationService.logoutUser();
  }
}
