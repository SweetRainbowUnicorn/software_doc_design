import { Moment } from 'moment';
import { ICompany } from 'app/shared/model/company.model';
import { ICurrency } from 'app/shared/model/currency.model';
import { IWorldIndex } from 'app/shared/model/world-index.model';
import { ISavedChart } from 'app/shared/model/saved-chart.model';

export interface IChart {
  id?: number;
  chartName?: string;
  itemId?: number;
  indicator?: number;
  xAxisStep?: number;
  yAxisStep?: number;
  startDate?: Moment;
  endDate?: Moment;
  companies?: ICompany[];
  currencies?: ICurrency[];
  indexes?: IWorldIndex[];
  savedChart?: ISavedChart;
}

export class Chart implements IChart {
  constructor(
    public id?: number,
    public chartName?: string,
    public itemId?: number,
    public indicator?: number,
    public xAxisStep?: number,
    public yAxisStep?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public companies?: ICompany[],
    public currencies?: ICurrency[],
    public indexes?: IWorldIndex[],
    public savedChart?: ISavedChart
  ) {}
}
