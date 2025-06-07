import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginUser } from '../../models/LoginUser';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Account } from '../../models/Account';
import { AccountLimit } from '../../models/AccountLimit';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

  constructor(private httpClient: HttpClient) { }


  getUserAccounts(): Observable<Account[]> {
    const getUserAccountsUrl = new URL(environment.apiGetUserAccountsUrl, environment.baseUrl).toString();

    return this.httpClient.get<Account[]>(getUserAccountsUrl, { headers: this.headers });
  }

  createAccount(): Observable<Account> {
    const createUserAccountsUrl = new URL(environment.apiCreateUserAccountUrl, environment.baseUrl).toString();

    return this.httpClient.post<Account>(createUserAccountsUrl, { headers: this.headers });
  }

  newAccountLimit(newLimit: AccountLimit): Observable<void> {

    const updateLimitUrl = new URL(environment.apiUpdateLimitUrl,environment.baseUrl).toString();

    return this.httpClient.put<void>(updateLimitUrl, newLimit, { headers: this.headers });
  }
}
