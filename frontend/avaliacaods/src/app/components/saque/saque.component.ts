import { Component } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { MatInputModule } from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-saque',
  imports: [NavBarComponent, MatInputModule, MatSelectModule,MatFormFieldModule,MatButtonModule, ReactiveFormsModule],
  templateUrl: './saque.component.html',
  styleUrl: './saque.component.scss'
})
export class SaqueComponent {
  foods: any[] = [
    { value: 'steak-0', viewValue: 'Steak' },
    { value: 'pizza-1', viewValue: 'Pizza' },
    { value: 'tacos-2', viewValue: 'Tacos' },
  ];

  valor = new FormControl("");
}
