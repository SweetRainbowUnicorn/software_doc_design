import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISavedChart } from 'app/shared/model/saved-chart.model';
import { SavedChartService } from './saved-chart.service';
import { SavedChartDeleteDialogComponent } from './saved-chart-delete-dialog.component';

@Component({
  selector: 'jhi-saved-chart',
  templateUrl: './saved-chart.component.html'
})
export class SavedChartComponent implements OnInit, OnDestroy {
  savedCharts?: ISavedChart[];
  eventSubscriber?: Subscription;

  constructor(protected savedChartService: SavedChartService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.savedChartService.query().subscribe((res: HttpResponse<ISavedChart[]>) => (this.savedCharts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSavedCharts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISavedChart): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSavedCharts(): void {
    this.eventSubscriber = this.eventManager.subscribe('savedChartListModification', () => this.loadAll());
  }

  delete(savedChart: ISavedChart): void {
    const modalRef = this.modalService.open(SavedChartDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.savedChart = savedChart;
  }
}
