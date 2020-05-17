import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IYahooUser } from 'app/shared/model/yahoo-user.model';
import { YahooUserService } from './yahoo-user.service';
import { YahooUserDeleteDialogComponent } from './yahoo-user-delete-dialog.component';

@Component({
  selector: 'jhi-yahoo-user',
  templateUrl: './yahoo-user.component.html'
})
export class YahooUserComponent implements OnInit, OnDestroy {
  yahooUsers?: IYahooUser[];
  eventSubscriber?: Subscription;

  constructor(protected yahooUserService: YahooUserService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.yahooUserService.query().subscribe((res: HttpResponse<IYahooUser[]>) => (this.yahooUsers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInYahooUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IYahooUser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInYahooUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('yahooUserListModification', () => this.loadAll());
  }

  delete(yahooUser: IYahooUser): void {
    const modalRef = this.modalService.open(YahooUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.yahooUser = yahooUser;
  }
}
