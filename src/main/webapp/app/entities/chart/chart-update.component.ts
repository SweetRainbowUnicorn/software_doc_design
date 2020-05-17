import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IChart, Chart } from 'app/shared/model/chart.model';
import { ChartService } from './chart.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency/currency.service';
import { IWorldIndex } from 'app/shared/model/world-index.model';
import { WorldIndexService } from 'app/entities/world-index/world-index.service';

type SelectableEntity = ICompany | ICurrency | IWorldIndex;

@Component({
  selector: 'jhi-chart-update',
  templateUrl: './chart-update.component.html'
})
export class ChartUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];
  currencies: ICurrency[] = [];
  worldindices: IWorldIndex[] = [];

  editForm = this.fb.group({
    id: [],
    chartName: [null, [Validators.required, Validators.maxLength(50)]],
    itemId: [null, [Validators.required]],
    indicator: [null, [Validators.required]],
    xAxisStep: [null, [Validators.required]],
    yAxisStep: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    companies: [],
    currencies: [],
    indexes: []
  });

  constructor(
    protected chartService: ChartService,
    protected companyService: CompanyService,
    protected currencyService: CurrencyService,
    protected worldIndexService: WorldIndexService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chart }) => {
      if (!chart.id) {
        const today = moment().startOf('day');
        chart.startDate = today;
        chart.endDate = today;
      }

      this.updateForm(chart);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));

      this.currencyService.query().subscribe((res: HttpResponse<ICurrency[]>) => (this.currencies = res.body || []));

      this.worldIndexService.query().subscribe((res: HttpResponse<IWorldIndex[]>) => (this.worldindices = res.body || []));
    });
  }

  updateForm(chart: IChart): void {
    this.editForm.patchValue({
      id: chart.id,
      chartName: chart.chartName,
      itemId: chart.itemId,
      indicator: chart.indicator,
      xAxisStep: chart.xAxisStep,
      yAxisStep: chart.yAxisStep,
      startDate: chart.startDate ? chart.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: chart.endDate ? chart.endDate.format(DATE_TIME_FORMAT) : null,
      companies: chart.companies,
      currencies: chart.currencies,
      indexes: chart.indexes
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chart = this.createFromForm();
    if (chart.id !== undefined) {
      this.subscribeToSaveResponse(this.chartService.update(chart));
    } else {
      this.subscribeToSaveResponse(this.chartService.create(chart));
    }
  }

  private createFromForm(): IChart {
    return {
      ...new Chart(),
      id: this.editForm.get(['id'])!.value,
      chartName: this.editForm.get(['chartName'])!.value,
      itemId: this.editForm.get(['itemId'])!.value,
      indicator: this.editForm.get(['indicator'])!.value,
      xAxisStep: this.editForm.get(['xAxisStep'])!.value,
      yAxisStep: this.editForm.get(['yAxisStep'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      companies: this.editForm.get(['companies'])!.value,
      currencies: this.editForm.get(['currencies'])!.value,
      indexes: this.editForm.get(['indexes'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChart>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
