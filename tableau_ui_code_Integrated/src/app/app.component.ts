import { Component, OnInit, Inject, HostListener } from "@angular/core";
import { TokenService } from "./service/token.service";
import { environment } from "src/environments/environment";
import { DOCUMENT } from "@angular/common";
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'tableau-ui';
  code: any;
  user?: any = [];
  userDetails: any = [];
  userId: any;
  constructor(
    @Inject(DOCUMENT) private document: Document,
    private tokenService: TokenService  ,
    private userService: UserService  
  ) { }

  ngOnInit() {


    this.fetchUserandToken();  
    this.fetchUserDetails();  
    //For setting tableau token manually 
  //  localStorage.setItem("tableau_token",  "8X-d3KhKRzmJTW8CgH2-EQ|SH6UZL30NTL6m4uOK1eyLk8M7NE5Xufk");  
  //  localStorage.setItem("tableau_token_last_update", new Date().getTime().toString());   
  }

  private fetchUserandToken() {
    if (localStorage.getItem("oam_user")) {
      this.userId = localStorage.getItem("oam_user");
    } else {
      if (localStorage.getItem("code")) {
        this.code = localStorage.getItem("code");
        this.tokenService.fetchTokenFromCode(this.code).subscribe(response => {
          this.userDetails = response;
          localStorage.removeItem("code");
          localStorage.setItem("oam_user", this.userDetails.id);
          localStorage.setItem("access_token", "Bearer " + this.userDetails.accessToken);
          localStorage.setItem("refresh_token", "Bearer " + this.userDetails.refreshToken);
          localStorage.setItem("access_token_last_update", new Date().getTime().toString());
          localStorage.setItem("refresh_token_last_update", new Date().getTime().toString());       
          localStorage.setItem("tableau_token",  this.userDetails.tableauToken);
          localStorage.setItem("tableau_token_last_update", new Date().getTime().toString());   
          this.userId = this.userDetails.id;
          this.fetchUserDetails();
        },
          error => {
            console.log("error", error);
            localStorage.clear();
            sessionStorage.clear();
            this.tokenService.deleteCookie().subscribe(() => {
              this.document.location.href = environment.getAuthorizationcode;
            });
          });
      } else {
        console.log("else condition");
        this.document.location.href = environment.getAuthorizationcode;
      } 
    }   
  }

  fetchUserDetails() {
    if (localStorage.getItem("oam_user")) {
    this.userService.getUserDetails().then(data => {     
      this.user = data;
      console.log("inside user details");
      console.log(this.user);

      localStorage.setItem("Role", this.user["role"]);
      localStorage.setItem("Region", this.user["region"]);
      
    });
   }
  }

  logout(){
    console.log("in logout");
    localStorage.clear();
    sessionStorage.clear();   
    window.location.href = environment.logoutUrl;      
}


}
