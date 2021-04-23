import { Injectable, Inject } from "@angular/core";
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpHeaders,
  HttpErrorResponse
} from '@angular/common/http';
import { finalize, catchError, switchMap } from "rxjs/operators";
import { Observable, Subject, throwError } from "rxjs";
import { DOCUMENT } from "@angular/common";
import { TokenService } from "../service/token.service";
import { environment } from "src/environments/environment";




@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  accessToken!: string;
  accessTokenLastUpdate: any;
  refreshToken!: string;
  refreshTokenLastUpdate: any;
  tableauToken!: string;
  tableauTokenLastUpdate: any;
  currentTime: any;
  count = 0;
  private refreshSubject: Subject<any> = new Subject<any>();
  private tableauTokenSubject:  Subject<any> = new Subject<any>();

  constructor( private tokenService: TokenService,
    @Inject(DOCUMENT) private document: Document) {}

   private ifTokenExpired(refreshToken: any) {
      this.refreshSubject.subscribe({
          complete: () => {
              this.refreshSubject = new Subject<any>();
          }
      });
      if (this.refreshSubject.observers.length === 1) {
          this.tokenService.fetchNewTableauAccessToken(refreshToken).subscribe(this.refreshSubject);
      }
      return this.refreshSubject;
  }


  private ifTableauTokenExpired(tableauToken: any) {
    this.tableauTokenSubject.subscribe({
        complete: () => {
            this.tableauTokenSubject = new Subject<any>();
        }
    });
    if (this.tableauTokenSubject.observers.length === 1) {
        this.tokenService.fetchNewAccessToken(tableauToken).subscribe(this.tableauTokenSubject);
    }
    return this.tableauTokenSubject;
}

  private checkTokenExpiryErr(error: HttpErrorResponse): boolean {
      return (
          error.status &&
          error.status === 401 ? true :false
      );
  }

  updateHeader(req: HttpRequest<any>) {
      const authToken = localStorage.getItem("access_token") || "";
      req = req.clone({
          headers: new HttpHeaders({             
              "Authorization": authToken
          })
      });

      return req;
  }


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {    
    this.accessToken = localStorage.getItem("access_token") || "";
    this.refreshToken = localStorage.getItem("refresh_token") || "";
    this.accessTokenLastUpdate = localStorage.getItem("access_token_last_update") || "";
    this.refreshTokenLastUpdate = localStorage.getItem("refresh_token_last_update") || "";
    this.tableauToken = localStorage.getItem("tableau_token") || "";
    this.tableauTokenLastUpdate = localStorage.getItem("tableau_token_last_update") || "";
    this.currentTime = new Date().getTime();
    const refreshTokentimeDifference = (this.currentTime - parseInt(this.refreshTokenLastUpdate)) / 1000;
    const accessTokentimeDifference = (this.currentTime - parseInt(this.accessTokenLastUpdate)) / 1000;
    const tableauTokentimeDifference = (this.currentTime - parseInt(this.tableauTokenLastUpdate)) / 1000;
    console.log("url is: "+req.url);
    
    if (req.url.endsWith("/token") || req.url.endsWith("/authorize") || req.url.endsWith("/cookie") ) {
      return next.handle(req);
    } else if (this.accessToken && this.refreshToken && req.url.startsWith(environment.getMiddlewareURL) ) {
        if (refreshTokentimeDifference >= environment.refreshTokenExpiry) {
            this.onRefreshTokenExpiry();
        } else if (accessTokentimeDifference >= environment.accessTokenExpiry) {
            return this.onAccessTokenExpiry(req, next);
        } else {   
            console.log("Token is : "+this.accessToken);     
            const authReq = req.clone({
                headers: new HttpHeaders({                    
                    "Authorization": this.accessToken
                })
            });
            //authReq.params.delete("hide");            
            console.log("Intercepted HTTP calls", authReq);
            return next.handle(authReq).pipe(
                finalize(() => {
                    this.count--;
                    // if (this.count <= 0 && !req.params.get("hide")) {
                    //     this.count = 0;
                    //     //this.spinner.hide();
                    // }
                })
            );
        }
    
    } else if(req.url.startsWith(environment.getTableauURL)){
        

        if (tableauTokentimeDifference >= environment.tableauTokenExpiry) {
            this.onTableauTokenExpiry(req, next);
        }else{
            console.log("Tableau Token is : "+this.accessToken);     
            const authReq = req.clone({
                headers: new HttpHeaders({                    
                    "X-Tableau-Auth": this.tableauToken,
                    "Accept": "application/json"
                })
            });
            //authReq.params.delete("hide");            
            console.log("Intercepted HTTP calls", authReq);
            return next.handle(authReq).pipe(
                finalize(() => {
                    this.count--;                   
                })
            );
        }
     } else {
        localStorage.clear();
        sessionStorage.clear();
        this.tokenService.deleteCookie().subscribe(() => {
            this.document.location.href = environment.getAuthorizationcode;
        });
    }

    return next.handle(req);
  }


private onAccessTokenExpiry(req: HttpRequest<any>, next: HttpHandler) {
   
  console.log("Intercepted HTTP calls", req);
  return next.handle(req).pipe(catchError((error, caught) => {
      if (error instanceof HttpErrorResponse) {
          console.log("error", error);

          if (this.checkTokenExpiryErr(error)) {
              return this.ifTokenExpired(this.refreshToken).pipe(switchMap((data) => {
                  localStorage.setItem("access_token", "Bearer " + data.accessToken);
                  localStorage.setItem("access_token_last_update", new Date().getTime().toString());
                  console.log("Intercepted HTTP calls", req);
                  return next.handle(this.updateHeader(req));
              }), finalize(() => {
                  this.count = 0;
                 // this.spinner.hide();
              }));
          } else {
              return throwError(error);
          }
      }
      return caught;
  }));
}

private onRefreshTokenExpiry() {
  localStorage.clear();
  sessionStorage.clear();
  window.location.href = environment.logoutUrl;
}

private onTableauTokenExpiry(req: HttpRequest<any>, next: HttpHandler) {
   
    console.log("Intercepted tableau  calls", req);
    return next.handle(req).pipe(catchError((error, caught) => {
        if (error instanceof HttpErrorResponse) {
            console.log("error", error);
  
            if (this.checkTokenExpiryErr(error)) {
                return this.ifTokenExpired(this.tableauToken).pipe(switchMap((data) => {
                    localStorage.setItem("tableau_token",  data.tableauToken);
                    localStorage.setItem("tableau_token_last_update", new Date().getTime().toString());                   
                    return next.handle(this.updateHeader(req));
                }), finalize(() => {
                    this.count = 0;                   
                }));
            } else {
                return throwError(error);
            }
        }
        return caught;
    }));
  }
  






}
