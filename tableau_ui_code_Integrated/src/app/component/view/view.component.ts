import { Component, OnInit } from '@angular/core';
import { StoreService } from 'src/app/service/store.service';
import {UserService} from '../../service/user.service'
declare var tableau: any;
@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
  ticket : any;
  report : any;

  constructor(private userService: UserService,
    private store : StoreService) {
    // var placeholderDiv = document.getElementById("tableauViz");
    // var url = "https://20.51.187.196/trusted/rV-WjVs_QLaAbk0tYCUwdw==:1BFh8yVD8vBO-L7CyTUMvvJ-/views/Regional/Obesity?:embed=y&:display_count=yes";

    // var options = {
    //   hideTabs: true,
    //   width: "800px",
    //   height: "700px"
    // };

    // var viz = new tableau.Viz(placeholderDiv, url, options);
    
  }

  ngOnInit() {
    this.getTrustedTicket();
    this.getView();
  }

  getTrustedTicket() {
    this.userService.getTrustedTicket().subscribe(data => {          
      this.ticket = data;      
    });
  }

  getView(){
    this.store.getViewState().subscribe((data:any) => {
      console.log(data);
      
      this.report = data;     
    })
  }

}
