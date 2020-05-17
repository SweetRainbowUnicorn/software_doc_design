import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'yahoo-user',
        loadChildren: () => import('./yahoo-user/yahoo-user.module').then(m => m.YahoofinanceYahooUserModule)
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.YahoofinanceCompanyModule)
      },
      {
        path: 'currency',
        loadChildren: () => import('./currency/currency.module').then(m => m.YahoofinanceCurrencyModule)
      },
      {
        path: 'world-index',
        loadChildren: () => import('./world-index/world-index.module').then(m => m.YahoofinanceWorldIndexModule)
      },
      {
        path: 'chart',
        loadChildren: () => import('./chart/chart.module').then(m => m.YahoofinanceChartModule)
      },
      {
        path: 'saved-chart',
        loadChildren: () => import('./saved-chart/saved-chart.module').then(m => m.YahoofinanceSavedChartModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class YahoofinanceEntityModule {}
