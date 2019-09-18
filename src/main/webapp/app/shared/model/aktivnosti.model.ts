import { IStrucnoUsavrsavanje } from 'app/shared/model/strucno-usavrsavanje.model';
import { INastavnik } from 'app/shared/model/nastavnik.model';

export interface IAktivnosti {
  id?: number;
  brojCitata?: number;
  brojRadova?: number;
  brojDomaciProjekata?: number;
  brojMedjunarodnihProjekata?: number;
  usavrsavanjas?: IStrucnoUsavrsavanje[];
  nastavnicis?: INastavnik[];
}

export const defaultValue: Readonly<IAktivnosti> = {};
