import { IMentorskiRad } from 'app/shared/model/mentorski-rad.model';

export interface IKandidat {
  id?: number;
  ime?: string;
  prezime?: string;
  jmbg?: string;
  diplome?: IMentorskiRad;
}

export const defaultValue: Readonly<IKandidat> = {};
