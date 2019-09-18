import { Moment } from 'moment';
import { INastavnik } from 'app/shared/model/nastavnik.model';

export interface IProjekat {
  id?: number;
  naziv?: string;
  vrstaProjekta?: string;
  datumZavrsetkaProjekta?: Moment;
  datumPocetkaProjekta?: Moment;
  nastavnicis?: INastavnik[];
}

export const defaultValue: Readonly<IProjekat> = {};
