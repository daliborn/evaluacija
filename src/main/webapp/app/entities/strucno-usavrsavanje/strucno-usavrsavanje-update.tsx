import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAktivnosti } from 'app/shared/model/aktivnosti.model';
import { getEntities as getAktivnostis } from 'app/entities/aktivnosti/aktivnosti.reducer';
import { getEntity, updateEntity, createEntity, reset } from './strucno-usavrsavanje.reducer';
import { IStrucnoUsavrsavanje } from 'app/shared/model/strucno-usavrsavanje.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStrucnoUsavrsavanjeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStrucnoUsavrsavanjeUpdateState {
  isNew: boolean;
  aktivnostiId: string;
}

export class StrucnoUsavrsavanjeUpdate extends React.Component<IStrucnoUsavrsavanjeUpdateProps, IStrucnoUsavrsavanjeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      aktivnostiId: '0',
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

    this.props.getAktivnostis();
  }

  saveEntity = (event, errors, values) => {
    values.pocetakUsavrsavanja = convertDateTimeToServer(values.pocetakUsavrsavanja);
    values.krajUsavrsavanja = convertDateTimeToServer(values.krajUsavrsavanja);

    if (errors.length === 0) {
      const { strucnoUsavrsavanjeEntity } = this.props;
      const entity = {
        ...strucnoUsavrsavanjeEntity,
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
    this.props.history.push('/entity/strucno-usavrsavanje');
  };

  render() {
    const { strucnoUsavrsavanjeEntity, aktivnostis, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.strucnoUsavrsavanje.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.home.createOrEditLabel">
                Create or edit a StrucnoUsavrsavanje
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : strucnoUsavrsavanjeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="strucno-usavrsavanje-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="strucno-usavrsavanje-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nazivLabel" for="strucno-usavrsavanje-naziv">
                    <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.naziv">Naziv</Translate>
                  </Label>
                  <AvField
                    id="strucno-usavrsavanje-naziv"
                    type="text"
                    name="naziv"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="pocetakUsavrsavanjaLabel" for="strucno-usavrsavanje-pocetakUsavrsavanja">
                    <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.pocetakUsavrsavanja">Pocetak Usavrsavanja</Translate>
                  </Label>
                  <AvInput
                    id="strucno-usavrsavanje-pocetakUsavrsavanja"
                    type="datetime-local"
                    className="form-control"
                    name="pocetakUsavrsavanja"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.strucnoUsavrsavanjeEntity.pocetakUsavrsavanja)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="krajUsavrsavanjaLabel" for="strucno-usavrsavanje-krajUsavrsavanja">
                    <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.krajUsavrsavanja">Kraj Usavrsavanja</Translate>
                  </Label>
                  <AvInput
                    id="strucno-usavrsavanje-krajUsavrsavanja"
                    type="datetime-local"
                    className="form-control"
                    name="krajUsavrsavanja"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.strucnoUsavrsavanjeEntity.krajUsavrsavanja)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="strucno-usavrsavanje-aktivnosti">
                    <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.aktivnosti">Aktivnosti</Translate>
                  </Label>
                  <AvInput id="strucno-usavrsavanje-aktivnosti" type="select" className="form-control" name="aktivnosti.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/strucno-usavrsavanje" replace color="info">
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
  aktivnostis: storeState.aktivnosti.entities,
  strucnoUsavrsavanjeEntity: storeState.strucnoUsavrsavanje.entity,
  loading: storeState.strucnoUsavrsavanje.loading,
  updating: storeState.strucnoUsavrsavanje.updating,
  updateSuccess: storeState.strucnoUsavrsavanje.updateSuccess
});

const mapDispatchToProps = {
  getAktivnostis,
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
)(StrucnoUsavrsavanjeUpdate);
