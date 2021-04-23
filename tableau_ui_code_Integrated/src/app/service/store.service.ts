import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  constructor() { }

  private ViewState$: BehaviorSubject<any> = new BehaviorSubject(null);

  getViewState(): Observable<any> {
      return this.ViewState$.asObservable();
  }

  setViewState(state: any) {
      this.ViewState$.next(state);
  }
  
}
