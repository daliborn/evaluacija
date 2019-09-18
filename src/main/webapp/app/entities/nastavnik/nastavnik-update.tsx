import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IZavrsniRad } from 'app/shared/model/zavrsni-rad.model';
import { getEntities as getZavrsniRads } from 'app/entities/zavrsni-rad/zavrsni-rad.reducer';
import { IProjekat } from 'app/shared/model/projekat.model';
import { getEntities as getProjekats } from 'app/entities/projekat/projekat.reducer';
import { IMentorskiRad } from 'app/shared/model/mentorski-rad.model';
import { getEntities as getMentorskiRads } from 'app/entities/mentorski-rad/mentorski-rad.reducer';
import { IAktivnosti } from 'app/shared/model/aktivnosti.model';
import { getEntities as getAktivnostis } from 'app/entities/aktivnosti/aktivnosti.reducer';
import { IOstaliPodaci } from 'app/shared/model/ostali-podaci.model';
import { getEntities as getOstaliPodacis } from 'app/entities/ostali-podaci/ostali-podaci.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './nastavnik.reducer';
import { INastavnik } from 'app/shared/model/nastavnik.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INastavnikUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INastavnikUpdateState {
  isNew: boolean;
  idszavrsniRadovi: any[];
  idsprojekti: any[];
  idsmentorskiRad: any[];
  idsaktivnosti: any[];
  idsostaliPodaci: any[];
}

export class NastavnikUpdate extends React.Component<INastavnikUpdateProps, INastavnikUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idszavrsniRadovi: [],
      idsprojekti: [],
      idsmentorskiRad: [],
      idsaktivnosti: [],
      idsostaliPodaci: [],
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getZavrsniRads();
    this.props.getProjekats();
    this.props.getMentorskiRads();
    this.props.getAktivnostis();
    this.props.getOstaliPodacis();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { nastavnikEntity } = this.props;
      const entity = {
        ...nastavnikEntity,
        ...values,
        zavrsniRadovis: mapIdList(values.zavrsniRadovis),
        projektis: mapIdList(values.projektis),
        mentorskiRads: mapIdList(values.mentorskiRads),
        aktivnostis: mapIdList(values.aktivnostis),
        ostaliPodacis: mapIdList(values.ostaliPodacis)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/nastavnik');
  };

  render() {
    const { nastavnikEntity, zavrsniRads, projekats, mentorskiRads, aktivnostis, ostaliPodacis, loading, updating } = this.props;
    const { isNew } = this.state;

    const { fotografija, fotografijaContentType } = nastavnikEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.nastavnik.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.nastavnik.home.createOrEditLabel">Create or edit a Nastavnik</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : nastavnikEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="nastavnik-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="nastavnik-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="imeLabel" for="nastavnik-ime">
                    <Translate contentKey="evaluacijaApp.nastavnik.ime">Ime</Translate>
                  </Label>
                  <AvField
                    id="nastavnik-ime"
                    type="text"
                    name="ime"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="prezimeLabel" for="nastavnik-prezime">
                    <Translate contentKey="evaluacijaApp.nastavnik.prezime">Prezime</Translate>
                  </Label>
                  <AvField
                    id="nastavnik-prezime"
                    type="text"
                    name="prezime"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="titulaLabel" for="nastavnik-titula">
                    <Translate contentKey="evaluacijaApp.nastavnik.titula">Titula</Translate>
                  </Label>
                  <AvField
                    id="nastavnik-titula"
                    type="text"
                    name="titula"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="fotografijaLabel" for="fotografija">
                      <Translate contentKey="evaluacijaApp.nastavnik.fotografija">Fotografija</Translate>
                    </Label>
                    <br />
                    {fotografija ? (
                      <div>
                        <a onClick={openFile(fotografijaContentType, fotografija)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {fotografijaContentType}, {byteSize(fotografija)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('fotografija')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_fotografija" type="file" onChange={this.onBlobChange(false, 'fotografija')} />
                    <AvInput type="hidden" name="fotografija" value={fotografija} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label for="nastavnik-zavrsniRadovi">
                    <Translate contentKey="evaluacijaApp.nastavnik.zavrsniRadovi">Zavrsni Radovi</Translate>
                  </Label>
                  <AvInput
                    id="nastavnik-zavrsniRadovi"
                    type="select"
                    multiple
                    className="form-control"
                    name="zavrsniRadovis"
                    value={nastavnikEntity.zavrsniRadovis && nastavnikEntity.zavrsniRadovis.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {zavrsniRads
                      ? zavrsniRads.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.naziv}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="nastavnik-projekti">
                    <Translate contentKey="evaluacijaApp.nastavnik.projekti">Projekti</Translate>
                  </Label>
                  <AvInput
                    id="nastavnik-projekti"
                    type="select"
                    multiple
                    className="form-control"
                    name="projektis"
                    value={nastavnikEntity.projektis && nastavnikEntity.projektis.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {projekats
                      ? projekats.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.naziv}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="nastavnik-mentorskiRad">
                    <Translate contentKey="evaluacijaApp.nastavnik.mentorskiRad">Mentorski Rad</Translate>
                  </Label>
                  <AvInput
                    id="nastavnik-mentorskiRad"
                    type="select"
                    multiple
                    className="form-control"
                    name="mentorskiRads"
                    value={nastavnikEntity.mentorskiRads && nastavnikEntity.mentorskiRads.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {mentorskiRads
                      ? mentorskiRads.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.naziv}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="nastavnik-aktivnosti">
                    <Translate contentKey="evaluacijaApp.nastavnik.aktivnosti">Aktivnosti</Translate>
                  </Label>
                  <AvInput
                    id="nastavnik-aktivnosti"
                    type="select"
                    multiple
                    className="form-control"
                    name="aktivnostis"
                    value={nastavnikEntity.aktivnostis && nastavnikEntity.aktivnostis.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {aktivnostis
                      ? aktivnostis.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="nastavnik-ostaliPodaci">
                    <Translate contentKey="evaluacijaApp.nastavnik.ostaliPodaci">Ostali Podaci</Translate>
                  </Label>
                  <AvInput
                    id="nastavnik-ostaliPodaci"
                    type="select"
                    multiple
                    className="form-control"
                    name="ostaliPodacis"
                    value={nastavnikEntity.ostaliPodacis && nastavnikEntity.ostaliPodacis.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {ostaliPodacis
                      ? ostaliPodacis.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.ostalo}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/nastavnik" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  zavrsniRads: storeState.zavrsniRad.entities,
  projekats: storeState.projekat.entities,
  mentorskiRads: storeState.mentorskiRad.entities,
  aktivnostis: storeState.aktivnosti.entities,
  ostaliPodacis: storeState.ostaliPodaci.entities,
  nastavnikEntity: storeState.nastavnik.entity,
  loading: storeState.nastavnik.loading,
  updating: storeState.nastavnik.updating,
  updateSuccess: storeState.nastavnik.updateSuccess
});

const mapDispatchToProps = {
  getZavrsniRads,
  getProjekats,
  getMentorskiRads,
  getAktivnostis,
  getOstaliPodacis,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NastavnikUpdate);
