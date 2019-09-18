import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
import sessions, { SessionsState } from 'app/modules/account/sessions/sessions.reducer';
// prettier-ignore
import nastavnik, {
  NastavnikState
} from 'app/entities/nastavnik/nastavnik.reducer';
// prettier-ignore
import zavrsniRad, {
  ZavrsniRadState
} from 'app/entities/zavrsni-rad/zavrsni-rad.reducer';
// prettier-ignore
import institucija, {
  InstitucijaState
} from 'app/entities/institucija/institucija.reducer';
// prettier-ignore
import projekat, {
  ProjekatState
} from 'app/entities/projekat/projekat.reducer';
// prettier-ignore
import mentorskiRad, {
  MentorskiRadState
} from 'app/entities/mentorski-rad/mentorski-rad.reducer';
// prettier-ignore
import aktivnosti, {
  AktivnostiState
} from 'app/entities/aktivnosti/aktivnosti.reducer';
// prettier-ignore
import ostaliPodaci, {
  OstaliPodaciState
} from 'app/entities/ostali-podaci/ostali-podaci.reducer';
// prettier-ignore
import naucneOblasti, {
  NaucneOblastiState
} from 'app/entities/naucne-oblasti/naucne-oblasti.reducer';
// prettier-ignore
import kandidat, {
  KandidatState
} from 'app/entities/kandidat/kandidat.reducer';
// prettier-ignore
import vrstaMentorstva, {
  VrstaMentorstvaState
} from 'app/entities/vrsta-mentorstva/vrsta-mentorstva.reducer';
// prettier-ignore
import strucnoUsavrsavanje, {
  StrucnoUsavrsavanjeState
} from 'app/entities/strucno-usavrsavanje/strucno-usavrsavanje.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly sessions: SessionsState;
  readonly nastavnik: NastavnikState;
  readonly zavrsniRad: ZavrsniRadState;
  readonly institucija: InstitucijaState;
  readonly projekat: ProjekatState;
  readonly mentorskiRad: MentorskiRadState;
  readonly aktivnosti: AktivnostiState;
  readonly ostaliPodaci: OstaliPodaciState;
  readonly naucneOblasti: NaucneOblastiState;
  readonly kandidat: KandidatState;
  readonly vrstaMentorstva: VrstaMentorstvaState;
  readonly strucnoUsavrsavanje: StrucnoUsavrsavanjeState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  sessions,
  nastavnik,
  zavrsniRad,
  institucija,
  projekat,
  mentorskiRad,
  aktivnosti,
  ostaliPodaci,
  naucneOblasti,
  kandidat,
  vrstaMentorstva,
  strucnoUsavrsavanje,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
