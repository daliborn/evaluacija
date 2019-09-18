import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OstaliPodaci from './ostali-podaci';
import OstaliPodaciDetail from './ostali-podaci-detail';
import OstaliPodaciUpdate from './ostali-podaci-update';
import OstaliPodaciDeleteDialog from './ostali-podaci-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OstaliPodaciUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OstaliPodaciUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OstaliPodaciDetail} />
      <ErrorBoundaryRoute path={match.url} component={OstaliPodaci} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OstaliPodaciDeleteDialog} />
  </>
);

export default Routes;
