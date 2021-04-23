// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

const middlewareURL = "http://localhost:8081/tableau-poc";
const tableauURL = "https://20.51.187.196/api/3.9/sites/3393a75b-4dbe-4481-9b59-3037c2fef20e";

export const environment = {
  production: false,
  getMiddlewareURL : middlewareURL,
  deletecookie: middlewareURL + '/cookie',
  getAuthorizationcode: middlewareURL + '/authorize',
  getTokenFromCode: middlewareURL + '/token',
  logoutUrl: middlewareURL + '/logout',
  getAccessToken: middlewareURL + '/accesstoken',  
  getUser : middlewareURL + '/user',
  getTableauAccessToken: middlewareURL + '/tableautoken',
  getTableauTrustedTicket: middlewareURL + '/tableautrustedticket',
  accessTokenExpiry: 3600,
  refreshTokenExpiry: 28800,

  getTableauURL : tableauURL,
  getProjects : tableauURL + '/projects',
  getViews : tableauURL + '/views',
  getWorkbooks : tableauURL + '/workbooks',
  getDatasources : tableauURL + '/datasources',
  getFlows : tableauURL + '/flows',
  tableauTokenExpiry : 7400
};



/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
