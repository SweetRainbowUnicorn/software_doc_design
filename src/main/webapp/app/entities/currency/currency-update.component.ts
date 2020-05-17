import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICurrency, Currency } from 'app/shared/model/currency.model';
import { CurrencyService } from './currency.service';

@Component({
  selector: 'jhi-currency-update',
  templateUrl: './currency-update.component.html'
})
export class CurrencyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    currencyName: [null, [Validators.required, Validators.maxLength(50)]],
    symbol: [null, [Validators.required, Validators.maxLength(50)]],
    lastPrice: [null, [Validators.required]],
    change: [null, [Validators.required]]
  });

  constructor(protected currencyService: CurrencyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currency }) => {
      this.updateForm(currency);
    });
  }

  updateForm(currency: ICurrency): void {
    this.editForm.patchValue({
      id: currency.id,
      currencyName: currency.currencyName,
      symbol: currency.symbol,
      lastPrice: currency.lastPrice,
      change: currency.change
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const currency = this.createFromForm();
    if (currency.id !== undefined) {
      this.subscribeToSaveResponse(this.currencyService.update(currency));
    } else {
      this.subscribeToSaveResponse(this.currencyService.create(currency));
    }
  }

  private createFromForm(): ICurrency {
    return {
      ...new Currency(),
      id: this.editForm.get(['id'])!.value,
      currencyName: this.editForm.get(['currencyName'])!.value,
      symbol: this.editForm.get(['symbol'])!.value,
      lastPrice: this.editForm.get(['lastPrice'])!.value,
      change: this.editForm.get(['change'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrency>>): void {
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
