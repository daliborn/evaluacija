import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ZavrsniRad from './zavrsni-rad';
import ZavrsniRadDetail from './zavrsni-rad-detail';
import ZavrsniRadUpdate from './zavrsni-rad-update';
import ZavrsniRadDeleteDialog from './zavrsni-rad-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ZavrsniRadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ZavrsniRadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ZavrsniRadDetail} />
      <ErrorBoundaryRoute path={match.url} component={ZavrsniRad} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ZavrsniRadDeleteDialog} />
  </>
);

export default Routes;
