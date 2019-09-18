import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './naucne-oblasti.reducer';
import { INaucneOblasti } from 'app/shared/model/naucne-oblasti.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INaucneOblastiProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class NaucneOblasti extends React.Component<INaucneOblastiProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { naucneOblastiList, match } = this.props;
    return (
      <div>
        <h2 id="naucne-oblasti-heading">
          <Translate contentKey="evaluacijaApp.naucneOblasti.home.title">Naucne Oblastis</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluacijaApp.naucneOblasti.home.createLabel">Create a new Naucne Oblasti</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {naucneOblastiList && naucneOblastiList.length > 0 ? (
            <Table responsive aria-describedby="naucne-oblasti-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.naucneOblasti.oblast">Oblast</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.naucneOblasti.zavrsniRadovi">Zavrsni Radovi</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {naucneOblastiList.map((naucneOblasti, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${naucneOblasti.id}`} color="link" size="sm">
                        {naucneOblasti.id}
                      </Button>
                    </td>
                    <td>{naucneOblasti.oblast}</td>
                    <td>
                      {naucneOblasti.zavrsniRadovi ? (
                        <Link to={`zavrsni-rad/${naucneOblasti.zavrsniRadovi.id}`}>{naucneOblasti.zavrsniRadovi.naziv}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${naucneOblasti.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${naucneOblasti.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${naucneOblasti.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="evaluacijaApp.naucneOblasti.home.notFound">No Naucne Oblastis found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ naucneOblasti }: IRootState) => ({
  naucneOblastiList: naucneOblasti.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NaucneOblasti);
