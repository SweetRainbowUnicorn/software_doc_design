import { IChart } from 'app/shared/model/chart.model';

export interface ICurrency {
  id?: number;
  currencyName?: string;
  symbol?: string;
  lastPrice?: number;
  change?: number;
  charts?: IChart[];
}

export class Currency implements ICurrency {
  constructor(
    public id?: number,
    public currencyName?: string,
    public symbol?: string,
    public lastPrice?: number,
    public change?: number,
    public charts?: IChart[]
  ) {}
}
