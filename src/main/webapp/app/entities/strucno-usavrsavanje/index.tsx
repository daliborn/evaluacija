import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StrucnoUsavrsavanje from './strucno-usavrsavanje';
import StrucnoUsavrsavanjeDetail from './strucno-usavrsavanje-detail';
import StrucnoUsavrsavanjeUpdate from './strucno-usavrsavanje-update';
import StrucnoUsavrsavanjeDeleteDialog from './strucno-usavrsavanje-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StrucnoUsavrsavanjeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StrucnoUsavrsavanjeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StrucnoUsavrsavanjeDetail} />
      <ErrorBoundaryRoute path={match.url} component={StrucnoUsavrsavanje} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={StrucnoUsavrsavanjeDeleteDialog} />
  </>
);

export default Routes;
