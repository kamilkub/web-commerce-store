import { TestBed } from '@angular/core/testing';

import { AuthorizationGuardianService } from './authorization-guardian.service';

describe('AuthorizationGuardianService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AuthorizationGuardianService = TestBed.get(AuthorizationGuardianService);
    expect(service).toBeTruthy();
  });
});
