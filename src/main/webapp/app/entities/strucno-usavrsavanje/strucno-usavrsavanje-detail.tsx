import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './strucno-usavrsavanje.reducer';
import { IStrucnoUsavrsavanje } from 'app/shared/model/strucno-usavrsavanje.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStrucnoUsavrsavanjeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StrucnoUsavrsavanjeDetail extends React.Component<IStrucnoUsavrsavanjeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { strucnoUsavrsavanjeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.detail.title">StrucnoUsavrsavanje</Translate> [
            <b>{strucnoUsavrsavanjeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="naziv">
                <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.naziv">Naziv</Translate>
              </span>
            </dt>
            <dd>{strucnoUsavrsavanjeEntity.naziv}</dd>
            <dt>
              <span id="pocetakUsavrsavanja">
                <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.pocetakUsavrsavanja">Pocetak Usavrsavanja</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={strucnoUsavrsavanjeEntity.pocetakUsavrsavanja} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="krajUsavrsavanja">
                <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.krajUsavrsavanja">Kraj Usavrsavanja</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={strucnoUsavrsavanjeEntity.krajUsavrsavanja} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.aktivnosti">Aktivnosti</Translate>
            </dt>
            <dd>{strucnoUsavrsavanjeEntity.aktivnosti ? strucnoUsavrsavanjeEntity.aktivnosti.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/strucno-usavrsavanje" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/strucno-usavrsavanje/${strucnoUsavrsavanjeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ strucnoUsavrsavanje }: IRootState) => ({
  strucnoUsavrsavanjeEntity: strucnoUsavrsavanje.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StrucnoUsavrsavanjeDetail);
