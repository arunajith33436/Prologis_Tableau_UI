import { Injectable, ErrorHandler } from "@angular/core";

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
  handleError(error: any): void {
    const chunkFailedMessage = /Loading chunk [\d]+ failed/;
    console.log(error);
    if (chunkFailedMessage.test(error.message)) {
      window.location.reload();
    }
  }
}
