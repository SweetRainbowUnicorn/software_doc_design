import { Moment } from 'moment';
import { IChart } from 'app/shared/model/chart.model';
import { IYahooUser } from 'app/shared/model/yahoo-user.model';

export interface ISavedChart {
  id?: number;
  chartId?: number;
  userId?: number;
  itemId?: number;
  indicator?: number;
  xAxisStep?: number;
  yAxisStep?: number;
  startDate?: Moment;
  endDate?: Moment;
  chart?: IChart;
  yahooUser?: IYahooUser;
}

export class SavedChart implements ISavedChart {
  constructor(
    public id?: number,
    public chartId?: number,
    public userId?: number,
    public itemId?: number,
    public indicator?: number,
    public xAxisStep?: number,
    public yAxisStep?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public chart?: IChart,
    public yahooUser?: IYahooUser
  ) {}
}
