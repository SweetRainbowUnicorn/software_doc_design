import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChart } from 'app/shared/model/chart.model';
import { ChartService } from './chart.service';

@Component({
  templateUrl: './chart-delete-dialog.component.html'
})
export class ChartDeleteDialogComponent {
  chart?: IChart;

  constructor(protected chartService: ChartService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.chartService.delete(id).subscribe(() => {
      this.eventManager.broadcast('chartListModification');
      this.activeModal.close();
    });
  }
}
