import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './zavrsni-rad.reducer';
import { IZavrsniRad } from 'app/shared/model/zavrsni-rad.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IZavrsniRadDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ZavrsniRadDetail extends React.Component<IZavrsniRadDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { zavrsniRadEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.zavrsniRad.detail.title">ZavrsniRad</Translate> [<b>{zavrsniRadEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="tipStudija">
                <Translate contentKey="evaluacijaApp.zavrsniRad.tipStudija">Tip Studija</Translate>
              </span>
            </dt>
            <dd>{zavrsniRadEntity.tipStudija}</dd>
            <dt>
              <span id="mentor">
                <Translate contentKey="evaluacijaApp.zavrsniRad.mentor">Mentor</Translate>
              </span>
            </dt>
            <dd>{zavrsniRadEntity.mentor}</dd>
            <dt>
              <span id="naziv">
                <Translate contentKey="evaluacijaApp.zavrsniRad.naziv">Naziv</Translate>
              </span>
            </dt>
            <dd>{zavrsniRadEntity.naziv}</dd>
            <dt>
              <span id="datumZavrsetkaRada">
                <Translate contentKey="evaluacijaApp.zavrsniRad.datumZavrsetkaRada">Datum Zavrsetka Rada</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={zavrsniRadEntity.datumZavrsetkaRada} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="evaluacijaApp.zavrsniRad.institucija">Institucija</Translate>
            </dt>
            <dd>{zavrsniRadEntity.institucija ? zavrsniRadEntity.institucija.naziv : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/zavrsni-rad" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/zavrsni-rad/${zavrsniRadEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ zavrsniRad }: IRootState) => ({
  zavrsniRadEntity: zavrsniRad.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ZavrsniRadDetail);
