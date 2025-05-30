import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginUser } from '../../models/LoginUser';
import { AuthenticationService } from '../../services/authentication/authentication.service';
import { ConfirmService } from '../../services/confirm/confirm.service';
import { CookieService } from 'ngx-cookie-service';
import { CommonModule } from '@angular/common';
import { cpfLengthValidator } from '../../validators/cpfLengthValidator';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';

@Component({
  selector: 'app-login',
  imports: [MatIconModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, CommonModule, NgxMaskDirective],
  providers:[provideNgxMask()],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {

  cpf = new FormControl('', [Validators.required, cpfLengthValidator()]);
  senha = new FormControl('', [Validators.required,Validators.minLength(3),Validators.maxLength(20)]);
  hidePassword = true;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private confirmService: ConfirmService,
    private cookieService: CookieService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['sessionExpired'] === 'true') {
        this.confirmService.errorAutoClose("Sua sessão expirou", "Por favor, faça login novamente");
      }
    });
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  goToRegister() {
    this.router.navigate(['/cliente']);
  }

  onLogin() {
    if (this.cpf.invalid || this.senha.invalid) {
      this.confirmService.error('Informações inválidas', 'Por favor, preencha todos os campos corretamente');
      return;
    }

    const loginUser: LoginUser = {
      cpf: this.cpf.value ?? '',
      senha: this.senha.value ?? ''
    };

    this.authenticationService.login(loginUser).subscribe({
      next: (data) => {
        this.cookieService.set('token', data.token, {
          expires: 1,
          sameSite: 'Strict',
          secure: true,
        });
        this.confirmService.successAutoClose('Login realizado com sucesso!', '');
        this.router.navigate(['/menu']);
      },
      error: (error) => {
        if (error.error && error.error.message) {
          this.confirmService.error(error.error.message, error.error.subMessage ?? "");
          return;
        }
        this.confirmService.error('Erro ao realizar login', 'Por favor, verifique as informações inseridas');
      }
    });
  }
}
