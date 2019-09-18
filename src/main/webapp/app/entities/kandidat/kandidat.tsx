import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './kandidat.reducer';
import { IKandidat } from 'app/shared/model/kandidat.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IKandidatProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Kandidat extends React.Component<IKandidatProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { kandidatList, match } = this.props;
    return (
      <div>
        <h2 id="kandidat-heading">
          <Translate contentKey="evaluacijaApp.kandidat.home.title">Kandidats</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluacijaApp.kandidat.home.createLabel">Create a new Kandidat</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {kandidatList && kandidatList.length > 0 ? (
            <Table responsive aria-describedby="kandidat-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.kandidat.ime">Ime</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.kandidat.prezime">Prezime</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.kandidat.jmbg">Jmbg</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {kandidatList.map((kandidat, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${kandidat.id}`} color="link" size="sm">
                        {kandidat.id}
                      </Button>
                    </td>
                    <td>{kandidat.ime}</td>
                    <td>{kandidat.prezime}</td>
                    <td>{kandidat.jmbg}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${kandidat.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${kandidat.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${kandidat.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="evaluacijaApp.kandidat.home.notFound">No Kandidats found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ kandidat }: IRootState) => ({
  kandidatList: kandidat.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Kandidat);
