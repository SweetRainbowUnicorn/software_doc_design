import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorldIndex } from 'app/shared/model/world-index.model';
import { WorldIndexService } from './world-index.service';
import { WorldIndexDeleteDialogComponent } from './world-index-delete-dialog.component';

@Component({
  selector: 'jhi-world-index',
  templateUrl: './world-index.component.html'
})
export class WorldIndexComponent implements OnInit, OnDestroy {
  worldIndices?: IWorldIndex[];
  eventSubscriber?: Subscription;

  constructor(protected worldIndexService: WorldIndexService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.worldIndexService.query().subscribe((res: HttpResponse<IWorldIndex[]>) => (this.worldIndices = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorldIndices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorldIndex): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorldIndices(): void {
    this.eventSubscriber = this.eventManager.subscribe('worldIndexListModification', () => this.loadAll());
  }

  delete(worldIndex: IWorldIndex): void {
    const modalRef = this.modalService.open(WorldIndexDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.worldIndex = worldIndex;
  }
}
