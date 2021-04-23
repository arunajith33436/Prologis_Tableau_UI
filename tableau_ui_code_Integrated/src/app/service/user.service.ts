import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../interface/user';
import { environment } from "src/environments/environment";
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  
  constructor(private httpClient: HttpClient) { }

  
  // getProjects(){
  //   return this.httpClient.get<any>('assets/data/projects.json')
  //   .toPromise()
  //   .then(res => <ProjectRootObject>res)
  //   .then(data => { return data; });
  // }

  getUserDetails(){
    return this.httpClient.get<any>(environment.getUser)
    .toPromise()
    .then(res => <User>res)
    .then(data => { return data; });
  }

  getTrustedTicket(){
    return this.httpClient
    .get(environment.getTableauTrustedTicket,{responseType:"text"})
    .pipe(map((response: any) => response));
    // return this.httpClient.get<any>(environment.getTableauTrustedTicket)
    // .toPromise()
    // .then(res => <Text>res)
    // .then(data => {console.log(data);
    //  return data; });
  }


}

