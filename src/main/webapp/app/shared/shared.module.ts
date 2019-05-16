import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AllocationSharedLibsModule, AllocationSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [AllocationSharedLibsModule, AllocationSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [AllocationSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AllocationSharedModule {
  static forRoot() {
    return {
      ngModule: AllocationSharedModule
    };
  }
}
