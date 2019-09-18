import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './nastavnik.reducer';
import { INastavnik } from 'app/shared/model/nastavnik.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INastavnikDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NastavnikDetail extends React.Component<INastavnikDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nastavnikEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.nastavnik.detail.title">Nastavnik</Translate> [<b>{nastavnikEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="ime">
                <Translate contentKey="evaluacijaApp.nastavnik.ime">Ime</Translate>
              </span>
            </dt>
            <dd>{nastavnikEntity.ime}</dd>
            <dt>
              <span id="prezime">
                <Translate contentKey="evaluacijaApp.nastavnik.prezime">Prezime</Translate>
              </span>
            </dt>
            <dd>{nastavnikEntity.prezime}</dd>
            <dt>
              <span id="titula">
                <Translate contentKey="evaluacijaApp.nastavnik.titula">Titula</Translate>
              </span>
            </dt>
            <dd>{nastavnikEntity.titula}</dd>
            <dt>
              <span id="fotografija">
                <Translate contentKey="evaluacijaApp.nastavnik.fotografija">Fotografija</Translate>
              </span>
            </dt>
            <dd>
              {nastavnikEntity.fotografija ? (
                <div>
                  <a onClick={openFile(nastavnikEntity.fotografijaContentType, nastavnikEntity.fotografija)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                  <span>
                    {nastavnikEntity.fotografijaContentType}, {byteSize(nastavnikEntity.fotografija)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <Translate contentKey="evaluacijaApp.nastavnik.zavrsniRadovi">Zavrsni Radovi</Translate>
            </dt>
            <dd>
              {nastavnikEntity.zavrsniRadovis
                ? nastavnikEntity.zavrsniRadovis.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.naziv}</a>
                      {i === nastavnikEntity.zavrsniRadovis.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="evaluacijaApp.nastavnik.projekti">Projekti</Translate>
            </dt>
            <dd>
              {nastavnikEntity.projektis
                ? nastavnikEntity.projektis.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.naziv}</a>
                      {i === nastavnikEntity.projektis.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="evaluacijaApp.nastavnik.mentorskiRad">Mentorski Rad</Translate>
            </dt>
            <dd>
              {nastavnikEntity.mentorskiRads
                ? nastavnikEntity.mentorskiRads.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.naziv}</a>
                      {i === nastavnikEntity.mentorskiRads.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="evaluacijaApp.nastavnik.aktivnosti">Aktivnosti</Translate>
            </dt>
            <dd>
              {nastavnikEntity.aktivnostis
                ? nastavnikEntity.aktivnostis.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === nastavnikEntity.aktivnostis.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="evaluacijaApp.nastavnik.ostaliPodaci">Ostali Podaci</Translate>
            </dt>
            <dd>
              {nastavnikEntity.ostaliPodacis
                ? nastavnikEntity.ostaliPodacis.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.ostalo}</a>
                      {i === nastavnikEntity.ostaliPodacis.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/nastavnik" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/nastavnik/${nastavnikEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ nastavnik }: IRootState) => ({
  nastavnikEntity: nastavnik.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NastavnikDetail);
