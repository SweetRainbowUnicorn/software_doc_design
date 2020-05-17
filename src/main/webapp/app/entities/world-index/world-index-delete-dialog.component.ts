import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorldIndex } from 'app/shared/model/world-index.model';
import { WorldIndexService } from './world-index.service';

@Component({
  templateUrl: './world-index-delete-dialog.component.html'
})
export class WorldIndexDeleteDialogComponent {
  worldIndex?: IWorldIndex;

  constructor(
    protected worldIndexService: WorldIndexService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.worldIndexService.delete(id).subscribe(() => {
      this.eventManager.broadcast('worldIndexListModification');
      this.activeModal.close();
    });
  }
}
