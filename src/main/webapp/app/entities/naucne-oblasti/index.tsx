import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NaucneOblasti from './naucne-oblasti';
import NaucneOblastiDetail from './naucne-oblasti-detail';
import NaucneOblastiUpdate from './naucne-oblasti-update';
import NaucneOblastiDeleteDialog from './naucne-oblasti-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NaucneOblastiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NaucneOblastiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NaucneOblastiDetail} />
      <ErrorBoundaryRoute path={match.url} component={NaucneOblasti} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NaucneOblastiDeleteDialog} />
  </>
);

export default Routes;
