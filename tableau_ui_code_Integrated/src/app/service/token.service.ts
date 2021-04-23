import { Injectable } from '@angular/core';
import { environment } from "src/environments/environment";
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(
    private httpClient: HttpClient
  ) { }

  deleteCookie() {
    return this.httpClient.delete(environment.deletecookie);
  }

  logout() {
    return this.httpClient.get(environment.logoutUrl);
  }

  fetchTokenFromCode(code: any) {
    const httpParams = new HttpParams().set("code", code);
    const options = { params: httpParams };
    return this.httpClient
      .get(environment.getTokenFromCode, options)
      .pipe(map((response: any) => response));
  }

  fetchNewAccessToken(refreshToken: string) {
    const headers = new HttpHeaders().set("Authorization", refreshToken);
    return this.httpClient
      .get(environment.getAccessToken, { headers: headers })
      .pipe(map((response: any) => response));
  }

  fetchNewTableauAccessToken(tableauToken: string) {
    const headers = new HttpHeaders().set("Authorization", tableauToken);
    return this.httpClient
      .get(environment.getTableauAccessToken, { headers: headers })
      .pipe(map((response: any) => response));
  }



}
