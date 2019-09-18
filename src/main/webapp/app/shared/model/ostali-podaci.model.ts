import { Moment } from 'moment';
import { INastavnik } from 'app/shared/model/nastavnik.model';

export interface IOstaliPodaci {
  id?: number;
  ostalo?: string;
  datum?: Moment;
  nastavnicis?: INastavnik[];
}

export const defaultValue: Readonly<IOstaliPodaci> = {};
