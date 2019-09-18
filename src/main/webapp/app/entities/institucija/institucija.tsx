import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './institucija.reducer';
import { IInstitucija } from 'app/shared/model/institucija.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInstitucijaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Institucija extends React.Component<IInstitucijaProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { institucijaList, match } = this.props;
    return (
      <div>
        <h2 id="institucija-heading">
          <Translate contentKey="evaluacijaApp.institucija.home.title">Institucijas</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluacijaApp.institucija.home.createLabel">Create a new Institucija</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {institucijaList && institucijaList.length > 0 ? (
            <Table responsive aria-describedby="institucija-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.institucija.sifra">Sifra</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.institucija.naziv">Naziv</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.institucija.mjesto">Mjesto</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {institucijaList.map((institucija, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${institucija.id}`} color="link" size="sm">
                        {institucija.id}
                      </Button>
                    </td>
                    <td>{institucija.sifra}</td>
                    <td>{institucija.naziv}</td>
                    <td>{institucija.mjesto}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${institucija.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${institucija.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${institucija.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="evaluacijaApp.institucija.home.notFound">No Institucijas found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ institucija }: IRootState) => ({
  institucijaList: institucija.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Institucija);
