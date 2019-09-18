import { IMentorskiRad } from 'app/shared/model/mentorski-rad.model';

export interface IVrstaMentorstva {
  id?: number;
  tip?: string;
  diplome?: IMentorskiRad;
}

export const defaultValue: Readonly<IVrstaMentorstva> = {};
