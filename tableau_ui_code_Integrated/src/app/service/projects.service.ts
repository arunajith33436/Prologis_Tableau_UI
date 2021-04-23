import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProjectRootObject } from '../interface/project';
import { environment } from "src/environments/environment";


@Injectable({
  providedIn: 'root'
})
export class ProjectsService {

  
  constructor(private httpClient: HttpClient) { }

  
  // getProjects(){
  //   return this.httpClient.get<any>('assets/data/projects.json')
  //   .toPromise()
  //   .then(res => <ProjectRootObject>res)
  //   .then(data => { return data; });
  // }

  getProjects(){
    return this.httpClient.get<any>(environment.getProjects)
     .toPromise()
    .then(res => <ProjectRootObject>res)
    .then(data => { return data; });
  }

  
  getWorkbooks(){
    return this.httpClient.get<any>(environment.getWorkbooks)
     .toPromise()
    .then(res => <any>res)
    .then(data => { return data; });
  }

  
  getViews(){
    return this.httpClient.get<any>(environment.getViews)
     .toPromise()
    .then(res => <any>res)
    .then(data => { return data; });
  }

}

