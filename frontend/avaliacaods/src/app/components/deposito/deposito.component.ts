import { Component } from '@angular/core';
import { NavBarComponent } from '../../components/nav-bar/nav-bar.component';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-deposito',
  imports: [NavBarComponent,MatInputModule, MatSelectModule,MatFormFieldModule,MatButtonModule, ReactiveFormsModule],
  templateUrl: './deposito.component.html',
  styleUrl: './deposito.component.scss'
})
export class DepositoComponent {
  foods: any[] = [
    { value: 'steak-0', viewValue: 'Steak' },
    { value: 'pizza-1', viewValue: 'Pizza' },
    { value: 'tacos-2', viewValue: 'Tacos' },
  ];

  valor = new FormControl("");
}
