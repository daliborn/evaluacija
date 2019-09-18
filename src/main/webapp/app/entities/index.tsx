import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Nastavnik from './nastavnik';
import ZavrsniRad from './zavrsni-rad';
import Institucija from './institucija';
import Projekat from './projekat';
import MentorskiRad from './mentorski-rad';
import Aktivnosti from './aktivnosti';
import OstaliPodaci from './ostali-podaci';
import NaucneOblasti from './naucne-oblasti';
import Kandidat from './kandidat';
import VrstaMentorstva from './vrsta-mentorstva';
import StrucnoUsavrsavanje from './strucno-usavrsavanje';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/nastavnik`} component={Nastavnik} />
      <ErrorBoundaryRoute path={`${match.url}/zavrsni-rad`} component={ZavrsniRad} />
      <ErrorBoundaryRoute path={`${match.url}/institucija`} component={Institucija} />
      <ErrorBoundaryRoute path={`${match.url}/projekat`} component={Projekat} />
      <ErrorBoundaryRoute path={`${match.url}/mentorski-rad`} component={MentorskiRad} />
      <ErrorBoundaryRoute path={`${match.url}/aktivnosti`} component={Aktivnosti} />
      <ErrorBoundaryRoute path={`${match.url}/ostali-podaci`} component={OstaliPodaci} />
      <ErrorBoundaryRoute path={`${match.url}/naucne-oblasti`} component={NaucneOblasti} />
      <ErrorBoundaryRoute path={`${match.url}/kandidat`} component={Kandidat} />
      <ErrorBoundaryRoute path={`${match.url}/vrsta-mentorstva`} component={VrstaMentorstva} />
      <ErrorBoundaryRoute path={`${match.url}/strucno-usavrsavanje`} component={StrucnoUsavrsavanje} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
