import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YahoofinanceSharedModule } from 'app/shared/shared.module';
import { SavedChartComponent } from './saved-chart.component';
import { SavedChartDetailComponent } from './saved-chart-detail.component';
import { SavedChartUpdateComponent } from './saved-chart-update.component';
import { SavedChartDeleteDialogComponent } from './saved-chart-delete-dialog.component';
import { savedChartRoute } from './saved-chart.route';

@NgModule({
  imports: [YahoofinanceSharedModule, RouterModule.forChild(savedChartRoute)],
  declarations: [SavedChartComponent, SavedChartDetailComponent, SavedChartUpdateComponent, SavedChartDeleteDialogComponent],
  entryComponents: [SavedChartDeleteDialogComponent]
})
export class YahoofinanceSavedChartModule {}
