import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './projekat.reducer';
import { IProjekat } from 'app/shared/model/projekat.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjekatDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProjekatDetail extends React.Component<IProjekatDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { projekatEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.projekat.detail.title">Projekat</Translate> [<b>{projekatEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="naziv">
                <Translate contentKey="evaluacijaApp.projekat.naziv">Naziv</Translate>
              </span>
            </dt>
            <dd>{projekatEntity.naziv}</dd>
            <dt>
              <span id="vrstaProjekta">
                <Translate contentKey="evaluacijaApp.projekat.vrstaProjekta">Vrsta Projekta</Translate>
              </span>
            </dt>
            <dd>{projekatEntity.vrstaProjekta}</dd>
            <dt>
              <span id="datumZavrsetkaProjekta">
                <Translate contentKey="evaluacijaApp.projekat.datumZavrsetkaProjekta">Datum Zavrsetka Projekta</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={projekatEntity.datumZavrsetkaProjekta} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="datumPocetkaProjekta">
                <Translate contentKey="evaluacijaApp.projekat.datumPocetkaProjekta">Datum Pocetka Projekta</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={projekatEntity.datumPocetkaProjekta} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/projekat" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/projekat/${projekatEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ projekat }: IRootState) => ({
  projekatEntity: projekat.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjekatDetail);
