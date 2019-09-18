import { IZavrsniRad } from 'app/shared/model/zavrsni-rad.model';
import { IProjekat } from 'app/shared/model/projekat.model';
import { IMentorskiRad } from 'app/shared/model/mentorski-rad.model';
import { IAktivnosti } from 'app/shared/model/aktivnosti.model';
import { IOstaliPodaci } from 'app/shared/model/ostali-podaci.model';

export interface INastavnik {
  id?: number;
  ime?: string;
  prezime?: string;
  titula?: string;
  fotografijaContentType?: string;
  fotografija?: any;
  zavrsniRadovis?: IZavrsniRad[];
  projektis?: IProjekat[];
  mentorskiRads?: IMentorskiRad[];
  aktivnostis?: IAktivnosti[];
  ostaliPodacis?: IOstaliPodaci[];
}

export const defaultValue: Readonly<INastavnik> = {};
