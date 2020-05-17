import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISavedChart } from 'app/shared/model/saved-chart.model';

@Component({
  selector: 'jhi-saved-chart-detail',
  templateUrl: './saved-chart-detail.component.html'
})
export class SavedChartDetailComponent implements OnInit {
  savedChart: ISavedChart | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ savedChart }) => (this.savedChart = savedChart));
  }

  previousState(): void {
    window.history.back();
  }
}
