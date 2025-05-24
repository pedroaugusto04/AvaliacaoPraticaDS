import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { ConfirmService } from '../../services/confirm/confirm.service';
import { User } from '../../models/User';

@Component({
  selector: 'app-nav-bar',
  imports: [],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent implements OnInit {

  user!: User;

  constructor(private userService: UserService, private confirmService: ConfirmService) {}
  
  ngOnInit() {
    this.userService.getUserLoggedIn().subscribe({
      next: (user) => {
        this.user = user;
      },
      error: () => {
        this.confirmService.error("Erro ao recuperar informações do usuário", "");
      }
    })
  }
}
