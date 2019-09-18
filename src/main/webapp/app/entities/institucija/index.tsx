import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Institucija from './institucija';
import InstitucijaDetail from './institucija-detail';
import InstitucijaUpdate from './institucija-update';
import InstitucijaDeleteDialog from './institucija-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InstitucijaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InstitucijaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InstitucijaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Institucija} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={InstitucijaDeleteDialog} />
  </>
);

export default Routes;
