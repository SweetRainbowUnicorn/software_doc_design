import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YahoofinanceSharedModule } from 'app/shared/shared.module';
import { WorldIndexComponent } from './world-index.component';
import { WorldIndexDetailComponent } from './world-index-detail.component';
import { WorldIndexUpdateComponent } from './world-index-update.component';
import { WorldIndexDeleteDialogComponent } from './world-index-delete-dialog.component';
import { worldIndexRoute } from './world-index.route';

@NgModule({
  imports: [YahoofinanceSharedModule, RouterModule.forChild(worldIndexRoute)],
  declarations: [WorldIndexComponent, WorldIndexDetailComponent, WorldIndexUpdateComponent, WorldIndexDeleteDialogComponent],
  entryComponents: [WorldIndexDeleteDialogComponent]
})
export class YahoofinanceWorldIndexModule {}
