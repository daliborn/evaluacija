import { IZavrsniRad } from 'app/shared/model/zavrsni-rad.model';

export interface INaucneOblasti {
  id?: number;
  oblast?: string;
  zavrsniRadovi?: IZavrsniRad;
}

export const defaultValue: Readonly<INaucneOblasti> = {};
