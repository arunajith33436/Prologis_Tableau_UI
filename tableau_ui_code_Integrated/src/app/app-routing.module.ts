import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ExploreComponent } from './component/explore/explore.component';
import { ViewComponent } from './component/view/view.component';

const routes: Routes = [
  { path: 'explore', component: ExploreComponent },
  { path: 'views', component: ViewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }


