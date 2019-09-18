import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VrstaMentorstva from './vrsta-mentorstva';
import VrstaMentorstvaDetail from './vrsta-mentorstva-detail';
import VrstaMentorstvaUpdate from './vrsta-mentorstva-update';
import VrstaMentorstvaDeleteDialog from './vrsta-mentorstva-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VrstaMentorstvaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VrstaMentorstvaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VrstaMentorstvaDetail} />
      <ErrorBoundaryRoute path={match.url} component={VrstaMentorstva} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VrstaMentorstvaDeleteDialog} />
  </>
);

export default Routes;
