import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISavedChart } from 'app/shared/model/saved-chart.model';
import { SavedChartService } from './saved-chart.service';

@Component({
  templateUrl: './saved-chart-delete-dialog.component.html'
})
export class SavedChartDeleteDialogComponent {
  savedChart?: ISavedChart;

  constructor(
    protected savedChartService: SavedChartService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.savedChartService.delete(id).subscribe(() => {
      this.eventManager.broadcast('savedChartListModification');
      this.activeModal.close();
    });
  }
}
