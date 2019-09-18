import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './aktivnosti.reducer';
import { IAktivnosti } from 'app/shared/model/aktivnosti.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAktivnostiProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Aktivnosti extends React.Component<IAktivnostiProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { aktivnostiList, match } = this.props;
    return (
      <div>
        <h2 id="aktivnosti-heading">
          <Translate contentKey="evaluacijaApp.aktivnosti.home.title">Aktivnostis</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluacijaApp.aktivnosti.home.createLabel">Create a new Aktivnosti</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {aktivnostiList && aktivnostiList.length > 0 ? (
            <Table responsive aria-describedby="aktivnosti-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.aktivnosti.brojCitata">Broj Citata</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.aktivnosti.brojRadova">Broj Radova</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.aktivnosti.brojDomaciProjekata">Broj Domaci Projekata</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.aktivnosti.brojMedjunarodnihProjekata">Broj Medjunarodnih Projekata</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {aktivnostiList.map((aktivnosti, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${aktivnosti.id}`} color="link" size="sm">
                        {aktivnosti.id}
                      </Button>
                    </td>
                    <td>{aktivnosti.brojCitata}</td>
                    <td>{aktivnosti.brojRadova}</td>
                    <td>{aktivnosti.brojDomaciProjekata}</td>
                    <td>{aktivnosti.brojMedjunarodnihProjekata}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${aktivnosti.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${aktivnosti.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${aktivnosti.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="evaluacijaApp.aktivnosti.home.notFound">No Aktivnostis found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ aktivnosti }: IRootState) => ({
  aktivnostiList: aktivnosti.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Aktivnosti);
