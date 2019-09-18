import { Moment } from 'moment';
import { IAktivnosti } from 'app/shared/model/aktivnosti.model';

export interface IStrucnoUsavrsavanje {
  id?: number;
  naziv?: string;
  pocetakUsavrsavanja?: Moment;
  krajUsavrsavanja?: Moment;
  aktivnosti?: IAktivnosti;
}

export const defaultValue: Readonly<IStrucnoUsavrsavanje> = {};
