import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Router } from '@angular/router';
import { Client } from '../../models/Client';
import { ClientService } from '../../services/client/client.service';
import { ConfirmService } from '../../services/confirm/confirm.service';

@Component({
  selector: 'app-cliente',
  imports: [MatInputModule,MatFormFieldModule, ReactiveFormsModule],
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.scss'
})
export class ClienteComponent {
  nome = new FormControl("");
  cpf = new FormControl("");
  senha = new FormControl("");
  telefone = new FormControl("");

  constructor(private router:Router, private clientService: ClientService, private confirmService: ConfirmService) {}


  goToLogin() {
    this.router.navigate(['/login']);
  }

  onRegister() {
    const client: Client = {
      nome: this.nome.value ?? "",
      cpf: this.cpf.value ?? "",
      senha: this.senha.value ?? "",
      telefone: this.telefone.value ?? ""
    };


    this.clientService.registerClient(client).subscribe({
      next:() => {
        this.confirmService.successAutoClose("Cadastro realizado com sucesso!","");
        this.router.navigate(['/login']);
      },
      error:(e) => {
        this.confirmService.error("Erro ao cadastrar","Por favor, verifique as informações inseridas");
      }
    })
  }
}
