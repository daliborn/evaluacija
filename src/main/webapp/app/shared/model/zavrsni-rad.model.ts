import { Moment } from 'moment';
import { INaucneOblasti } from 'app/shared/model/naucne-oblasti.model';
import { IInstitucija } from 'app/shared/model/institucija.model';
import { INastavnik } from 'app/shared/model/nastavnik.model';

export interface IZavrsniRad {
  id?: number;
  tipStudija?: string;
  mentor?: string;
  naziv?: string;
  datumZavrsetkaRada?: Moment;
  oblastis?: INaucneOblasti[];
  institucija?: IInstitucija;
  nastavnicis?: INastavnik[];
}

export const defaultValue: Readonly<IZavrsniRad> = {};
