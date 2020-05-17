import { IChart } from 'app/shared/model/chart.model';

export interface IWorldIndex {
  id?: number;
  indexName?: string;
  lastPrice?: number;
  change?: number;
  volume?: number;
  charts?: IChart[];
}

export class WorldIndex implements IWorldIndex {
  constructor(
    public id?: number,
    public indexName?: string,
    public lastPrice?: number,
    public change?: number,
    public volume?: number,
    public charts?: IChart[]
  ) {}
}
