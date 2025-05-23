import { Component, OnInit } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule } from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [MatIconModule,ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  cpf = new FormControl("");
  password = new FormControl("");

  constructor(private router: Router) {}

  goToRegister() {
    this.router.navigate(['/cliente'])
  }

  onLogin() {
    //
    this.router.navigate(['/menu'])
  }
}
