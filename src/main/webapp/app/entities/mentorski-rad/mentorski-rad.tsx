import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './mentorski-rad.reducer';
import { IMentorskiRad } from 'app/shared/model/mentorski-rad.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMentorskiRadProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class MentorskiRad extends React.Component<IMentorskiRadProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { mentorskiRadList, match } = this.props;
    return (
      <div>
        <h2 id="mentorski-rad-heading">
          <Translate contentKey="evaluacijaApp.mentorskiRad.home.title">Mentorski Rads</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluacijaApp.mentorskiRad.home.createLabel">Create a new Mentorski Rad</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {mentorskiRadList && mentorskiRadList.length > 0 ? (
            <Table responsive aria-describedby="mentorski-rad-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.mentorskiRad.naziv">Naziv</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.mentorskiRad.datumPocetkaMentorskogRada">Datum Pocetka Mentorskog Rada</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.mentorskiRad.datumZavrsetkaMentorskogRada">
                      Datum Zavrsetka Mentorskog Rada
                    </Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.mentorskiRad.oblasti">Oblasti</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.mentorskiRad.vrstaMentorstva">Vrsta Mentorstva</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {mentorskiRadList.map((mentorskiRad, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${mentorskiRad.id}`} color="link" size="sm">
                        {mentorskiRad.id}
                      </Button>
                    </td>
                    <td>{mentorskiRad.naziv}</td>
                    <td>
                      <TextFormat type="date" value={mentorskiRad.datumPocetkaMentorskogRada} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={mentorskiRad.datumZavrsetkaMentorskogRada} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {mentorskiRad.oblasti ? <Link to={`kandidat/${mentorskiRad.oblasti.id}`}>{mentorskiRad.oblasti.prezime}</Link> : ''}
                    </td>
                    <td>
                      {mentorskiRad.vrstaMentorstva ? (
                        <Link to={`vrsta-mentorstva/${mentorskiRad.vrstaMentorstva.id}`}>{mentorskiRad.vrstaMentorstva.tip}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${mentorskiRad.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${mentorskiRad.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${mentorskiRad.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="evaluacijaApp.mentorskiRad.home.notFound">No Mentorski Rads found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ mentorskiRad }: IRootState) => ({
  mentorskiRadList: mentorskiRad.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MentorskiRad);
