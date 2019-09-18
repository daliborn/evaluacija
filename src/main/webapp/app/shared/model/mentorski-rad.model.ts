import { Moment } from 'moment';
import { IKandidat } from 'app/shared/model/kandidat.model';
import { IVrstaMentorstva } from 'app/shared/model/vrsta-mentorstva.model';
import { INastavnik } from 'app/shared/model/nastavnik.model';

export interface IMentorskiRad {
  id?: number;
  naziv?: string;
  datumPocetkaMentorskogRada?: Moment;
  datumZavrsetkaMentorskogRada?: Moment;
  oblasti?: IKandidat;
  vrstaMentorstva?: IVrstaMentorstva;
  nastavnicis?: INastavnik[];
}

export const defaultValue: Readonly<IMentorskiRad> = {};
