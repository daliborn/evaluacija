import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IKandidat } from 'app/shared/model/kandidat.model';
import { getEntities as getKandidats } from 'app/entities/kandidat/kandidat.reducer';
import { IVrstaMentorstva } from 'app/shared/model/vrsta-mentorstva.model';
import { getEntities as getVrstaMentorstvas } from 'app/entities/vrsta-mentorstva/vrsta-mentorstva.reducer';
import { INastavnik } from 'app/shared/model/nastavnik.model';
import { getEntities as getNastavniks } from 'app/entities/nastavnik/nastavnik.reducer';
import { getEntity, updateEntity, createEntity, reset } from './mentorski-rad.reducer';
import { IMentorskiRad } from 'app/shared/model/mentorski-rad.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMentorskiRadUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMentorskiRadUpdateState {
  isNew: boolean;
  oblastiId: string;
  vrstaMentorstvaId: string;
  nastavniciId: string;
}

export class MentorskiRadUpdate extends React.Component<IMentorskiRadUpdateProps, IMentorskiRadUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      oblastiId: '0',
      vrstaMentorstvaId: '0',
      nastavniciId: '0',
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

    this.props.getKandidats();
    this.props.getVrstaMentorstvas();
    this.props.getNastavniks();
  }

  saveEntity = (event, errors, values) => {
    values.datumPocetkaMentorskogRada = convertDateTimeToServer(values.datumPocetkaMentorskogRada);
    values.datumZavrsetkaMentorskogRada = convertDateTimeToServer(values.datumZavrsetkaMentorskogRada);

    if (errors.length === 0) {
      const { mentorskiRadEntity } = this.props;
      const entity = {
        ...mentorskiRadEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/mentorski-rad');
  };

  render() {
    const { mentorskiRadEntity, kandidats, vrstaMentorstvas, nastavniks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.mentorskiRad.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.mentorskiRad.home.createOrEditLabel">Create or edit a MentorskiRad</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mentorskiRadEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="mentorski-rad-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="mentorski-rad-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nazivLabel" for="mentorski-rad-naziv">
                    <Translate contentKey="evaluacijaApp.mentorskiRad.naziv">Naziv</Translate>
                  </Label>
                  <AvField
                    id="mentorski-rad-naziv"
                    type="text"
                    name="naziv"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="datumPocetkaMentorskogRadaLabel" for="mentorski-rad-datumPocetkaMentorskogRada">
                    <Translate contentKey="evaluacijaApp.mentorskiRad.datumPocetkaMentorskogRada">Datum Pocetka Mentorskog Rada</Translate>
                  </Label>
                  <AvInput
                    id="mentorski-rad-datumPocetkaMentorskogRada"
                    type="datetime-local"
                    className="form-control"
                    name="datumPocetkaMentorskogRada"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.mentorskiRadEntity.datumPocetkaMentorskogRada)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="datumZavrsetkaMentorskogRadaLabel" for="mentorski-rad-datumZavrsetkaMentorskogRada">
                    <Translate contentKey="evaluacijaApp.mentorskiRad.datumZavrsetkaMentorskogRada">
                      Datum Zavrsetka Mentorskog Rada
                    </Translate>
                  </Label>
                  <AvInput
                    id="mentorski-rad-datumZavrsetkaMentorskogRada"
                    type="datetime-local"
                    className="form-control"
                    name="datumZavrsetkaMentorskogRada"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.mentorskiRadEntity.datumZavrsetkaMentorskogRada)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="mentorski-rad-oblasti">
                    <Translate contentKey="evaluacijaApp.mentorskiRad.oblasti">Oblasti</Translate>
                  </Label>
                  <AvInput id="mentorski-rad-oblasti" type="select" className="form-control" name="oblasti.id">
                    <option value="" key="0" />
                    {kandidats
                      ? kandidats.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.prezime}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="mentorski-rad-vrstaMentorstva">
                    <Translate contentKey="evaluacijaApp.mentorskiRad.vrstaMentorstva">Vrsta Mentorstva</Translate>
                  </Label>
                  <AvInput id="mentorski-rad-vrstaMentorstva" type="select" className="form-control" name="vrstaMentorstva.id">
                    <option value="" key="0" />
                    {vrstaMentorstvas
                      ? vrstaMentorstvas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.tip}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/mentorski-rad" replace color="info">
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
  kandidats: storeState.kandidat.entities,
  vrstaMentorstvas: storeState.vrstaMentorstva.entities,
  nastavniks: storeState.nastavnik.entities,
  mentorskiRadEntity: storeState.mentorskiRad.entity,
  loading: storeState.mentorskiRad.loading,
  updating: storeState.mentorskiRad.updating,
  updateSuccess: storeState.mentorskiRad.updateSuccess
});

const mapDispatchToProps = {
  getKandidats,
  getVrstaMentorstvas,
  getNastavniks,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MentorskiRadUpdate);
