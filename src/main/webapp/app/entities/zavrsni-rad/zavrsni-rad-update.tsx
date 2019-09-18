import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IInstitucija } from 'app/shared/model/institucija.model';
import { getEntities as getInstitucijas } from 'app/entities/institucija/institucija.reducer';
import { INastavnik } from 'app/shared/model/nastavnik.model';
import { getEntities as getNastavniks } from 'app/entities/nastavnik/nastavnik.reducer';
import { getEntity, updateEntity, createEntity, reset } from './zavrsni-rad.reducer';
import { IZavrsniRad } from 'app/shared/model/zavrsni-rad.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IZavrsniRadUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IZavrsniRadUpdateState {
  isNew: boolean;
  institucijaId: string;
  nastavniciId: string;
}

export class ZavrsniRadUpdate extends React.Component<IZavrsniRadUpdateProps, IZavrsniRadUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      institucijaId: '0',
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

    this.props.getInstitucijas();
    this.props.getNastavniks();
  }

  saveEntity = (event, errors, values) => {
    values.datumZavrsetkaRada = convertDateTimeToServer(values.datumZavrsetkaRada);

    if (errors.length === 0) {
      const { zavrsniRadEntity } = this.props;
      const entity = {
        ...zavrsniRadEntity,
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
    this.props.history.push('/entity/zavrsni-rad');
  };

  render() {
    const { zavrsniRadEntity, institucijas, nastavniks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.zavrsniRad.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.zavrsniRad.home.createOrEditLabel">Create or edit a ZavrsniRad</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : zavrsniRadEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="zavrsni-rad-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="zavrsni-rad-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="tipStudijaLabel" for="zavrsni-rad-tipStudija">
                    <Translate contentKey="evaluacijaApp.zavrsniRad.tipStudija">Tip Studija</Translate>
                  </Label>
                  <AvField
                    id="zavrsni-rad-tipStudija"
                    type="text"
                    name="tipStudija"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="mentorLabel" for="zavrsni-rad-mentor">
                    <Translate contentKey="evaluacijaApp.zavrsniRad.mentor">Mentor</Translate>
                  </Label>
                  <AvField id="zavrsni-rad-mentor" type="text" name="mentor" />
                </AvGroup>
                <AvGroup>
                  <Label id="nazivLabel" for="zavrsni-rad-naziv">
                    <Translate contentKey="evaluacijaApp.zavrsniRad.naziv">Naziv</Translate>
                  </Label>
                  <AvField
                    id="zavrsni-rad-naziv"
                    type="text"
                    name="naziv"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="datumZavrsetkaRadaLabel" for="zavrsni-rad-datumZavrsetkaRada">
                    <Translate contentKey="evaluacijaApp.zavrsniRad.datumZavrsetkaRada">Datum Zavrsetka Rada</Translate>
                  </Label>
                  <AvInput
                    id="zavrsni-rad-datumZavrsetkaRada"
                    type="datetime-local"
                    className="form-control"
                    name="datumZavrsetkaRada"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.zavrsniRadEntity.datumZavrsetkaRada)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="zavrsni-rad-institucija">
                    <Translate contentKey="evaluacijaApp.zavrsniRad.institucija">Institucija</Translate>
                  </Label>
                  <AvInput id="zavrsni-rad-institucija" type="select" className="form-control" name="institucija.id">
                    <option value="" key="0" />
                    {institucijas
                      ? institucijas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.naziv}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/zavrsni-rad" replace color="info">
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
  institucijas: storeState.institucija.entities,
  nastavniks: storeState.nastavnik.entities,
  zavrsniRadEntity: storeState.zavrsniRad.entity,
  loading: storeState.zavrsniRad.loading,
  updating: storeState.zavrsniRad.updating,
  updateSuccess: storeState.zavrsniRad.updateSuccess
});

const mapDispatchToProps = {
  getInstitucijas,
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
)(ZavrsniRadUpdate);
