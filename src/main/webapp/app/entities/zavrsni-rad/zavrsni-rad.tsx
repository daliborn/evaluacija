import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './zavrsni-rad.reducer';
import { IZavrsniRad } from 'app/shared/model/zavrsni-rad.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IZavrsniRadProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ZavrsniRad extends React.Component<IZavrsniRadProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { zavrsniRadList, match } = this.props;
    return (
      <div>
        <h2 id="zavrsni-rad-heading">
          <Translate contentKey="evaluacijaApp.zavrsniRad.home.title">Zavrsni Rads</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluacijaApp.zavrsniRad.home.createLabel">Create a new Zavrsni Rad</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {zavrsniRadList && zavrsniRadList.length > 0 ? (
            <Table responsive aria-describedby="zavrsni-rad-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.zavrsniRad.tipStudija">Tip Studija</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.zavrsniRad.mentor">Mentor</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.zavrsniRad.naziv">Naziv</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.zavrsniRad.datumZavrsetkaRada">Datum Zavrsetka Rada</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.zavrsniRad.institucija">Institucija</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {zavrsniRadList.map((zavrsniRad, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${zavrsniRad.id}`} color="link" size="sm">
                        {zavrsniRad.id}
                      </Button>
                    </td>
                    <td>{zavrsniRad.tipStudija}</td>
                    <td>{zavrsniRad.mentor}</td>
                    <td>{zavrsniRad.naziv}</td>
                    <td>
                      <TextFormat type="date" value={zavrsniRad.datumZavrsetkaRada} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {zavrsniRad.institucija ? (
                        <Link to={`institucija/${zavrsniRad.institucija.id}`}>{zavrsniRad.institucija.naziv}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${zavrsniRad.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${zavrsniRad.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${zavrsniRad.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="evaluacijaApp.zavrsniRad.home.notFound">No Zavrsni Rads found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ zavrsniRad }: IRootState) => ({
  zavrsniRadList: zavrsniRad.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ZavrsniRad);
