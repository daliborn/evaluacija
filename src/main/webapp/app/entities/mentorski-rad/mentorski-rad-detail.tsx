import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './mentorski-rad.reducer';
import { IMentorskiRad } from 'app/shared/model/mentorski-rad.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMentorskiRadDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MentorskiRadDetail extends React.Component<IMentorskiRadDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mentorskiRadEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.mentorskiRad.detail.title">MentorskiRad</Translate> [<b>{mentorskiRadEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="naziv">
                <Translate contentKey="evaluacijaApp.mentorskiRad.naziv">Naziv</Translate>
              </span>
            </dt>
            <dd>{mentorskiRadEntity.naziv}</dd>
            <dt>
              <span id="datumPocetkaMentorskogRada">
                <Translate contentKey="evaluacijaApp.mentorskiRad.datumPocetkaMentorskogRada">Datum Pocetka Mentorskog Rada</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={mentorskiRadEntity.datumPocetkaMentorskogRada} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="datumZavrsetkaMentorskogRada">
                <Translate contentKey="evaluacijaApp.mentorskiRad.datumZavrsetkaMentorskogRada">Datum Zavrsetka Mentorskog Rada</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={mentorskiRadEntity.datumZavrsetkaMentorskogRada} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="evaluacijaApp.mentorskiRad.oblasti">Oblasti</Translate>
            </dt>
            <dd>{mentorskiRadEntity.oblasti ? mentorskiRadEntity.oblasti.prezime : ''}</dd>
            <dt>
              <Translate contentKey="evaluacijaApp.mentorskiRad.vrstaMentorstva">Vrsta Mentorstva</Translate>
            </dt>
            <dd>{mentorskiRadEntity.vrstaMentorstva ? mentorskiRadEntity.vrstaMentorstva.tip : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/mentorski-rad" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/mentorski-rad/${mentorskiRadEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ mentorskiRad }: IRootState) => ({
  mentorskiRadEntity: mentorskiRad.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MentorskiRadDetail);
