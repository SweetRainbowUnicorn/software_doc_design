import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IYahooUser, YahooUser } from 'app/shared/model/yahoo-user.model';
import { YahooUserService } from './yahoo-user.service';

@Component({
  selector: 'jhi-yahoo-user-update',
  templateUrl: './yahoo-user-update.component.html'
})
export class YahooUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    username: [null, [Validators.required, Validators.maxLength(50)]],
    age: [null, [Validators.required]],
    interest: [null, [Validators.maxLength(50)]]
  });

  constructor(protected yahooUserService: YahooUserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ yahooUser }) => {
      this.updateForm(yahooUser);
    });
  }

  updateForm(yahooUser: IYahooUser): void {
    this.editForm.patchValue({
      id: yahooUser.id,
      username: yahooUser.username,
      age: yahooUser.age,
      interest: yahooUser.interest
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const yahooUser = this.createFromForm();
    if (yahooUser.id !== undefined) {
      this.subscribeToSaveResponse(this.yahooUserService.update(yahooUser));
    } else {
      this.subscribeToSaveResponse(this.yahooUserService.create(yahooUser));
    }
  }

  private createFromForm(): IYahooUser {
    return {
      ...new YahooUser(),
      id: this.editForm.get(['id'])!.value,
      username: this.editForm.get(['username'])!.value,
      age: this.editForm.get(['age'])!.value,
      interest: this.editForm.get(['interest'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IYahooUser>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
