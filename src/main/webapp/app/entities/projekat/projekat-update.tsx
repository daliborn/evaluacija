import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INastavnik } from 'app/shared/model/nastavnik.model';
import { getEntities as getNastavniks } from 'app/entities/nastavnik/nastavnik.reducer';
import { getEntity, updateEntity, createEntity, reset } from './projekat.reducer';
import { IProjekat } from 'app/shared/model/projekat.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProjekatUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProjekatUpdateState {
  isNew: boolean;
  nastavniciId: string;
}

export class ProjekatUpdate extends React.Component<IProjekatUpdateProps, IProjekatUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getNastavniks();
  }

  saveEntity = (event, errors, values) => {
    values.datumZavrsetkaProjekta = convertDateTimeToServer(values.datumZavrsetkaProjekta);
    values.datumPocetkaProjekta = convertDateTimeToServer(values.datumPocetkaProjekta);

    if (errors.length === 0) {
      const { projekatEntity } = this.props;
      const entity = {
        ...projekatEntity,
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
    this.props.history.push('/entity/projekat');
  };

  render() {
    const { projekatEntity, nastavniks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.projekat.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.projekat.home.createOrEditLabel">Create or edit a Projekat</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : projekatEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="projekat-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="projekat-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nazivLabel" for="projekat-naziv">
                    <Translate contentKey="evaluacijaApp.projekat.naziv">Naziv</Translate>
                  </Label>
                  <AvField
                    id="projekat-naziv"
                    type="text"
                    name="naziv"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="vrstaProjektaLabel" for="projekat-vrstaProjekta">
                    <Translate contentKey="evaluacijaApp.projekat.vrstaProjekta">Vrsta Projekta</Translate>
                  </Label>
                  <AvField
                    id="projekat-vrstaProjekta"
                    type="text"
                    name="vrstaProjekta"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="datumZavrsetkaProjektaLabel" for="projekat-datumZavrsetkaProjekta">
                    <Translate contentKey="evaluacijaApp.projekat.datumZavrsetkaProjekta">Datum Zavrsetka Projekta</Translate>
                  </Label>
                  <AvInput
                    id="projekat-datumZavrsetkaProjekta"
                    type="datetime-local"
                    className="form-control"
                    name="datumZavrsetkaProjekta"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.projekatEntity.datumZavrsetkaProjekta)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="datumPocetkaProjektaLabel" for="projekat-datumPocetkaProjekta">
                    <Translate contentKey="evaluacijaApp.projekat.datumPocetkaProjekta">Datum Pocetka Projekta</Translate>
                  </Label>
                  <AvInput
                    id="projekat-datumPocetkaProjekta"
                    type="datetime-local"
                    className="form-control"
                    name="datumPocetkaProjekta"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.projekatEntity.datumPocetkaProjekta)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/projekat" replace color="info">
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
  nastavniks: storeState.nastavnik.entities,
  projekatEntity: storeState.projekat.entity,
  loading: storeState.projekat.loading,
  updating: storeState.projekat.updating,
  updateSuccess: storeState.projekat.updateSuccess
});

const mapDispatchToProps = {
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
)(ProjekatUpdate);
