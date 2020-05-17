import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChart } from 'app/shared/model/chart.model';
import { ChartService } from './chart.service';
import { ChartDeleteDialogComponent } from './chart-delete-dialog.component';

@Component({
  selector: 'jhi-chart',
  templateUrl: './chart.component.html'
})
export class ChartComponent implements OnInit, OnDestroy {
  charts?: IChart[];
  eventSubscriber?: Subscription;

  constructor(protected chartService: ChartService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.chartService.query().subscribe((res: HttpResponse<IChart[]>) => (this.charts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCharts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IChart): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCharts(): void {
    this.eventSubscriber = this.eventManager.subscribe('chartListModification', () => this.loadAll());
  }

  delete(chart: IChart): void {
    const modalRef = this.modalService.open(ChartDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.chart = chart;
  }
}
