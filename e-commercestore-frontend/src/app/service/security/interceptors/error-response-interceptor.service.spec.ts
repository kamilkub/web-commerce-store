import { TestBed } from '@angular/core/testing';

import { ErrorResponseInterceptorService } from './error-response-interceptor.service';

describe('ErrorResponseInterceptorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ErrorResponseInterceptorService = TestBed.get(ErrorResponseInterceptorService);
    expect(service).toBeTruthy();
  });
});
