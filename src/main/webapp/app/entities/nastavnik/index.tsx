import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Nastavnik from './nastavnik';
import NastavnikDetail from './nastavnik-detail';
import NastavnikUpdate from './nastavnik-update';
import NastavnikDeleteDialog from './nastavnik-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NastavnikUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NastavnikUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NastavnikDetail} />
      <ErrorBoundaryRoute path={match.url} component={Nastavnik} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NastavnikDeleteDialog} />
  </>
);

export default Routes;
