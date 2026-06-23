import {HttpErrorResponse} from '@angular/common/http';

export function httpErrorMessage(error: unknown, fallback: string): string {
  if (error instanceof HttpErrorResponse) {
    const body = error.error as { message?: string } | string | null;
    if (typeof body === 'string' && body.length > 0) {
      return body;
    }
    if (body && typeof body === 'object' && typeof body.message === 'string') {
      return body.message;
    }
    return error.message || fallback;
  }
  if (error instanceof Error) {
    return error.message;
  }
  return fallback;
}
