import { Component, OnInit } from '@angular/core';
import { ProjectsService } from 'src/app/service/projects.service';
import { Router } from '@angular/router';
import { StoreService } from 'src/app/service/store.service';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.css']
})
export class ExploreComponent implements OnInit {

  projectsList?: any[];
  workbooksList?: any[];
  viewsList?: any[];
  userRole: any = "staticRole";
  userRegion: any = "staticRegion";
  tableau:any;
  constructor(private projectsService: ProjectsService,
    private router: Router,
    private store : StoreService) { }

  ngOnInit(): void {
    this.fetchProjects();
  }

  fetchProjects() {
    
    this.projectsService.getProjects().then(data => {
      this.projectsList = data.projects.project;      
    });

    this.userRole = localStorage.getItem("Role");
    this.userRegion = localStorage.getItem("Region");

  }

  
  fetchWorkbooks(id: any) {    
    this.workbooksList = [];
    this.projectsService.getWorkbooks().then(data => {
      let workbooks = data.workbooks.workbook.filter((item: any) => { return item.project.id === id});
      this.workbooksList = workbooks;      
    });
  }

  fetchViews(id: any) {    
    this.projectsService.getViews().then(data => {
      let views = data.views.view.filter((item: any) => { return item.workbook.id === id});
      this.viewsList = views;        
    });
  }

  renderView(id:any,report: any) {
    let workbook = this.workbooksList?.find((data)=>{return data.id === id});    
    this.store.setViewState(workbook.name+'/'+report)
    this.router.navigateByUrl('/views');
};


}
