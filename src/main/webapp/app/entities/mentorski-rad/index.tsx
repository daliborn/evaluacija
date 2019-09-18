import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MentorskiRad from './mentorski-rad';
import MentorskiRadDetail from './mentorski-rad-detail';
import MentorskiRadUpdate from './mentorski-rad-update';
import MentorskiRadDeleteDialog from './mentorski-rad-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MentorskiRadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MentorskiRadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MentorskiRadDetail} />
      <ErrorBoundaryRoute path={match.url} component={MentorskiRad} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MentorskiRadDeleteDialog} />
  </>
);

export default Routes;
