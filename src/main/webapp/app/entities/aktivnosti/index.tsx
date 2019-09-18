import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Aktivnosti from './aktivnosti';
import AktivnostiDetail from './aktivnosti-detail';
import AktivnostiUpdate from './aktivnosti-update';
import AktivnostiDeleteDialog from './aktivnosti-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AktivnostiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AktivnostiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AktivnostiDetail} />
      <ErrorBoundaryRoute path={match.url} component={Aktivnosti} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AktivnostiDeleteDialog} />
  </>
);

export default Routes;
