import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './ostali-podaci.reducer';
import { IOstaliPodaci } from 'app/shared/model/ostali-podaci.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOstaliPodaciProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class OstaliPodaci extends React.Component<IOstaliPodaciProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { ostaliPodaciList, match } = this.props;
    return (
      <div>
        <h2 id="ostali-podaci-heading">
          <Translate contentKey="evaluacijaApp.ostaliPodaci.home.title">Ostali Podacis</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluacijaApp.ostaliPodaci.home.createLabel">Create a new Ostali Podaci</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {ostaliPodaciList && ostaliPodaciList.length > 0 ? (
            <Table responsive aria-describedby="ostali-podaci-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.ostaliPodaci.ostalo">Ostalo</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.ostaliPodaci.datum">Datum</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {ostaliPodaciList.map((ostaliPodaci, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${ostaliPodaci.id}`} color="link" size="sm">
                        {ostaliPodaci.id}
                      </Button>
                    </td>
                    <td>{ostaliPodaci.ostalo}</td>
                    <td>
                      <TextFormat type="date" value={ostaliPodaci.datum} format={APP_DATE_FORMAT} />
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${ostaliPodaci.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${ostaliPodaci.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${ostaliPodaci.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="evaluacijaApp.ostaliPodaci.home.notFound">No Ostali Podacis found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ ostaliPodaci }: IRootState) => ({
  ostaliPodaciList: ostaliPodaci.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OstaliPodaci);
