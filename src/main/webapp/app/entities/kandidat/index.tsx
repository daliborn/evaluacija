import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Kandidat from './kandidat';
import KandidatDetail from './kandidat-detail';
import KandidatUpdate from './kandidat-update';
import KandidatDeleteDialog from './kandidat-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KandidatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KandidatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KandidatDetail} />
      <ErrorBoundaryRoute path={match.url} component={Kandidat} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={KandidatDeleteDialog} />
  </>
);

export default Routes;
