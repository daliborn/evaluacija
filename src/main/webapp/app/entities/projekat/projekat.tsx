import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './projekat.reducer';
import { IProjekat } from 'app/shared/model/projekat.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjekatProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Projekat extends React.Component<IProjekatProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { projekatList, match } = this.props;
    return (
      <div>
        <h2 id="projekat-heading">
          <Translate contentKey="evaluacijaApp.projekat.home.title">Projekats</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="evaluacijaApp.projekat.home.createLabel">Create a new Projekat</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {projekatList && projekatList.length > 0 ? (
            <Table responsive aria-describedby="projekat-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.projekat.naziv">Naziv</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.projekat.vrstaProjekta">Vrsta Projekta</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.projekat.datumZavrsetkaProjekta">Datum Zavrsetka Projekta</Translate>
                  </th>
                  <th>
                    <Translate contentKey="evaluacijaApp.projekat.datumPocetkaProjekta">Datum Pocetka Projekta</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {projekatList.map((projekat, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${projekat.id}`} color="link" size="sm">
                        {projekat.id}
                      </Button>
                    </td>
                    <td>{projekat.naziv}</td>
                    <td>{projekat.vrstaProjekta}</td>
                    <td>
                      <TextFormat type="date" value={projekat.datumZavrsetkaProjekta} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={projekat.datumPocetkaProjekta} format={APP_DATE_FORMAT} />
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${projekat.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${projekat.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${projekat.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="evaluacijaApp.projekat.home.notFound">No Projekats found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ projekat }: IRootState) => ({
  projekatList: projekat.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Projekat);
