import { IChart } from 'app/shared/model/chart.model';

export interface ICompany {
  id?: number;
  companyId?: number;
  companyName?: string;
  industry?: string;
  marketCap?: number;
  price?: number;
  charts?: IChart[];
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public companyId?: number,
    public companyName?: string,
    public industry?: string,
    public marketCap?: number,
    public price?: number,
    public charts?: IChart[]
  ) {}
}
