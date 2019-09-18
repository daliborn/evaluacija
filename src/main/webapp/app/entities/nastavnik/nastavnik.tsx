import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './nastavnik.reducer';
import { INastavnik } from 'app/shared/model/nastavnik.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INastavnikProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Nastavnik extends React.Component<INastavnikProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { nastavnikList, match } = this.props;
    return (
      <div>
        <h2 id="nastavnik-heading">
          <Translate contentKey="evaluacijaApp.nastavnik.home.title">Nastavniks</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluacijaApp.nastavnik.home.createLabel">Create a new Nastavnik</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {nastavnikList && nastavnikList.length > 0 ? (
            <Table responsive aria-describedby="nastavnik-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.nastavnik.ime">Ime</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.nastavnik.prezime">Prezime</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.nastavnik.titula">Titula</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.nastavnik.fotografija">Fotografija</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.nastavnik.zavrsniRadovi">Zavrsni Radovi</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.nastavnik.projekti">Projekti</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.nastavnik.mentorskiRad">Mentorski Rad</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.nastavnik.aktivnosti">Aktivnosti</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.nastavnik.ostaliPodaci">Ostali Podaci</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {nastavnikList.map((nastavnik, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${nastavnik.id}`} color="link" size="sm">
                        {nastavnik.id}
                      </Button>
                    </td>
                    <td>{nastavnik.ime}</td>
                    <td>{nastavnik.prezime}</td>
                    <td>{nastavnik.titula}</td>
                    <td>
                      {nastavnik.fotografija ? (
                        <div>
                          <a onClick={openFile(nastavnik.fotografijaContentType, nastavnik.fotografija)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                          <span>
                            {nastavnik.fotografijaContentType}, {byteSize(nastavnik.fotografija)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>
                      {nastavnik.zavrsniRadovis
                        ? nastavnik.zavrsniRadovis.map((val, j) => (
                            <span key={j}>
                              <Link to={`zavrsni-rad/${val.id}`}>{val.naziv}</Link>
                              {j === nastavnik.zavrsniRadovis.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td>
                      {nastavnik.projektis
                        ? nastavnik.projektis.map((val, j) => (
                            <span key={j}>
                              <Link to={`projekat/${val.id}`}>{val.naziv}</Link>
                              {j === nastavnik.projektis.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td>
                      {nastavnik.mentorskiRads
                        ? nastavnik.mentorskiRads.map((val, j) => (
                            <span key={j}>
                              <Link to={`mentorski-rad/${val.id}`}>{val.naziv}</Link>
                              {j === nastavnik.mentorskiRads.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td>
                      {nastavnik.aktivnostis
                        ? nastavnik.aktivnostis.map((val, j) => (
                            <span key={j}>
                              <Link to={`aktivnosti/${val.id}`}>{val.id}</Link>
                              {j === nastavnik.aktivnostis.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td>
                      {nastavnik.ostaliPodacis
                        ? nastavnik.ostaliPodacis.map((val, j) => (
                            <span key={j}>
                              <Link to={`ostali-podaci/${val.id}`}>{val.ostalo}</Link>
                              {j === nastavnik.ostaliPodacis.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${nastavnik.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${nastavnik.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${nastavnik.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="evaluacijaApp.nastavnik.home.notFound">No Nastavniks found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ nastavnik }: IRootState) => ({
  nastavnikList: nastavnik.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Nastavnik);
