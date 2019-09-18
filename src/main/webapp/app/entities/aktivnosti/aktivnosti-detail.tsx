import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './aktivnosti.reducer';
import { IAktivnosti } from 'app/shared/model/aktivnosti.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAktivnostiDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AktivnostiDetail extends React.Component<IAktivnostiDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { aktivnostiEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.aktivnosti.detail.title">Aktivnosti</Translate> [<b>{aktivnostiEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="brojCitata">
                <Translate contentKey="evaluacijaApp.aktivnosti.brojCitata">Broj Citata</Translate>
              </span>
            </dt>
            <dd>{aktivnostiEntity.brojCitata}</dd>
            <dt>
              <span id="brojRadova">
                <Translate contentKey="evaluacijaApp.aktivnosti.brojRadova">Broj Radova</Translate>
              </span>
            </dt>
            <dd>{aktivnostiEntity.brojRadova}</dd>
            <dt>
              <span id="brojDomaciProjekata">
                <Translate contentKey="evaluacijaApp.aktivnosti.brojDomaciProjekata">Broj Domaci Projekata</Translate>
              </span>
            </dt>
            <dd>{aktivnostiEntity.brojDomaciProjekata}</dd>
            <dt>
              <span id="brojMedjunarodnihProjekata">
                <Translate contentKey="evaluacijaApp.aktivnosti.brojMedjunarodnihProjekata">Broj Medjunarodnih Projekata</Translate>
              </span>
            </dt>
            <dd>{aktivnostiEntity.brojMedjunarodnihProjekata}</dd>
          </dl>
          <Button tag={Link} to="/entity/aktivnosti" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/aktivnosti/${aktivnostiEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ aktivnosti }: IRootState) => ({
  aktivnostiEntity: aktivnosti.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AktivnostiDetail);
