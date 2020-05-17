import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISavedChart, SavedChart } from 'app/shared/model/saved-chart.model';
import { SavedChartService } from './saved-chart.service';
import { IChart } from 'app/shared/model/chart.model';
import { ChartService } from 'app/entities/chart/chart.service';
import { IYahooUser } from 'app/shared/model/yahoo-user.model';
import { YahooUserService } from 'app/entities/yahoo-user/yahoo-user.service';

type SelectableEntity = IChart | IYahooUser;

@Component({
  selector: 'jhi-saved-chart-update',
  templateUrl: './saved-chart-update.component.html'
})
export class SavedChartUpdateComponent implements OnInit {
  isSaving = false;
  charts: IChart[] = [];
  yahoousers: IYahooUser[] = [];

  editForm = this.fb.group({
    id: [],
    chartId: [null, [Validators.required]],
    userId: [null, [Validators.required]],
    itemId: [null, [Validators.required]],
    indicator: [null, [Validators.required]],
    xAxisStep: [null, [Validators.required]],
    yAxisStep: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    chart: [],
    yahooUser: []
  });

  constructor(
    protected savedChartService: SavedChartService,
    protected chartService: ChartService,
    protected yahooUserService: YahooUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ savedChart }) => {
      if (!savedChart.id) {
        const today = moment().startOf('day');
        savedChart.startDate = today;
        savedChart.endDate = today;
      }

      this.updateForm(savedChart);

      this.chartService
        .query({ filter: 'savedchart-is-null' })
        .pipe(
          map((res: HttpResponse<IChart[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IChart[]) => {
          if (!savedChart.chart || !savedChart.chart.id) {
            this.charts = resBody;
          } else {
            this.chartService
              .find(savedChart.chart.id)
              .pipe(
                map((subRes: HttpResponse<IChart>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IChart[]) => (this.charts = concatRes));
          }
        });

      this.yahooUserService.query().subscribe((res: HttpResponse<IYahooUser[]>) => (this.yahoousers = res.body || []));
    });
  }

  updateForm(savedChart: ISavedChart): void {
    this.editForm.patchValue({
      id: savedChart.id,
      chartId: savedChart.chartId,
      userId: savedChart.userId,
      itemId: savedChart.itemId,
      indicator: savedChart.indicator,
      xAxisStep: savedChart.xAxisStep,
      yAxisStep: savedChart.yAxisStep,
      startDate: savedChart.startDate ? savedChart.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: savedChart.endDate ? savedChart.endDate.format(DATE_TIME_FORMAT) : null,
      chart: savedChart.chart,
      yahooUser: savedChart.yahooUser
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const savedChart = this.createFromForm();
    if (savedChart.id !== undefined) {
      this.subscribeToSaveResponse(this.savedChartService.update(savedChart));
    } else {
      this.subscribeToSaveResponse(this.savedChartService.create(savedChart));
    }
  }

  private createFromForm(): ISavedChart {
    return {
      ...new SavedChart(),
      id: this.editForm.get(['id'])!.value,
      chartId: this.editForm.get(['chartId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      itemId: this.editForm.get(['itemId'])!.value,
      indicator: this.editForm.get(['indicator'])!.value,
      xAxisStep: this.editForm.get(['xAxisStep'])!.value,
      yAxisStep: this.editForm.get(['yAxisStep'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      chart: this.editForm.get(['chart'])!.value,
      yahooUser: this.editForm.get(['yahooUser'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISavedChart>>): void {
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
}
