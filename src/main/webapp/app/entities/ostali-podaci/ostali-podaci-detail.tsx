import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ostali-podaci.reducer';
import { IOstaliPodaci } from 'app/shared/model/ostali-podaci.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOstaliPodaciDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OstaliPodaciDetail extends React.Component<IOstaliPodaciDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ostaliPodaciEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.ostaliPodaci.detail.title">OstaliPodaci</Translate> [<b>{ostaliPodaciEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="ostalo">
                <Translate contentKey="evaluacijaApp.ostaliPodaci.ostalo">Ostalo</Translate>
              </span>
            </dt>
            <dd>{ostaliPodaciEntity.ostalo}</dd>
            <dt>
              <span id="datum">
                <Translate contentKey="evaluacijaApp.ostaliPodaci.datum">Datum</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={ostaliPodaciEntity.datum} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/ostali-podaci" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/ostali-podaci/${ostaliPodaciEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ostaliPodaci }: IRootState) => ({
  ostaliPodaciEntity: ostaliPodaci.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OstaliPodaciDetail);
