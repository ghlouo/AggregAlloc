import { NgModule } from '@angular/core';

import { AllocationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [AllocationSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [AllocationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AllocationSharedCommonModule {}
