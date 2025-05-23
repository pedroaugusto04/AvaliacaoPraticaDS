import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cliente',
  imports: [MatInputModule,MatFormFieldModule, ReactiveFormsModule],
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.scss'
})
export class ClienteComponent {
  name = new FormControl("");
  cpf = new FormControl("");
  password = new FormControl("");
  phone = new FormControl("");

  constructor(private router:Router) {}


  goToLogin() {
    this.router.navigate(['/login']);
  }

  onRegister() {
    //
    this.router.navigate(['/login']);
  }
}
