import { IZavrsniRad } from 'app/shared/model/zavrsni-rad.model';

export interface IInstitucija {
  id?: number;
  sifra?: string;
  naziv?: string;
  mjesto?: string;
  zavrsniRadovis?: IZavrsniRad[];
}

export const defaultValue: Readonly<IInstitucija> = {};
