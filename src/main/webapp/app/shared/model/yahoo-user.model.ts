import { ISavedChart } from 'app/shared/model/saved-chart.model';

export interface IYahooUser {
  id?: number;
  username?: string;
  age?: number;
  interest?: string;
  savedCharts?: ISavedChart[];
}

export class YahooUser implements IYahooUser {
  constructor(
    public id?: number,
    public username?: string,
    public age?: number,
    public interest?: string,
    public savedCharts?: ISavedChart[]
  ) {}
}
