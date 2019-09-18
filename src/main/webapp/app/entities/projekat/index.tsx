import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Projekat from './projekat';
import ProjekatDetail from './projekat-detail';
import ProjekatUpdate from './projekat-update';
import ProjekatDeleteDialog from './projekat-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjekatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjekatUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjekatDetail} />
      <ErrorBoundaryRoute path={match.url} component={Projekat} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProjekatDeleteDialog} />
  </>
);

export default Routes;
