import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YahoofinanceSharedModule } from 'app/shared/shared.module';
import { YahooUserComponent } from './yahoo-user.component';
import { YahooUserDetailComponent } from './yahoo-user-detail.component';
import { YahooUserUpdateComponent } from './yahoo-user-update.component';
import { YahooUserDeleteDialogComponent } from './yahoo-user-delete-dialog.component';
import { yahooUserRoute } from './yahoo-user.route';

@NgModule({
  imports: [YahoofinanceSharedModule, RouterModule.forChild(yahooUserRoute)],
  declarations: [YahooUserComponent, YahooUserDetailComponent, YahooUserUpdateComponent, YahooUserDeleteDialogComponent],
  entryComponents: [YahooUserDeleteDialogComponent]
})
export class YahoofinanceYahooUserModule {}
