import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { YahoofinanceSharedModule } from 'app/shared/shared.module';
import { ChartComponent } from './chart.component';
import { ChartDetailComponent } from './chart-detail.component';
import { ChartUpdateComponent } from './chart-update.component';
import { ChartDeleteDialogComponent } from './chart-delete-dialog.component';
import { chartRoute } from './chart.route';

@NgModule({
  imports: [YahoofinanceSharedModule, RouterModule.forChild(chartRoute)],
  declarations: [ChartComponent, ChartDetailComponent, ChartUpdateComponent, ChartDeleteDialogComponent],
  entryComponents: [ChartDeleteDialogComponent]
})
export class YahoofinanceChartModule {}
