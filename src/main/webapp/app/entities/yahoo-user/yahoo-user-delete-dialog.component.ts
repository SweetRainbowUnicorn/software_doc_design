import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IYahooUser } from 'app/shared/model/yahoo-user.model';
import { YahooUserService } from './yahoo-user.service';

@Component({
  templateUrl: './yahoo-user-delete-dialog.component.html'
})
export class YahooUserDeleteDialogComponent {
  yahooUser?: IYahooUser;

  constructor(protected yahooUserService: YahooUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.yahooUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('yahooUserListModification');
      this.activeModal.close();
    });
  }
}
