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
import { getEntity, updateEntity, createEntity, reset } from './ostali-podaci.reducer';
import { IOstaliPodaci } from 'app/shared/model/ostali-podaci.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOstaliPodaciUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOstaliPodaciUpdateState {
  isNew: boolean;
  nastavniciId: string;
}

export class OstaliPodaciUpdate extends React.Component<IOstaliPodaciUpdateProps, IOstaliPodaciUpdateState> {
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
    values.datum = convertDateTimeToServer(values.datum);

    if (errors.length === 0) {
      const { ostaliPodaciEntity } = this.props;
      const entity = {
        ...ostaliPodaciEntity,
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
    this.props.history.push('/entity/ostali-podaci');
  };

  render() {
    const { ostaliPodaciEntity, nastavniks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.ostaliPodaci.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.ostaliPodaci.home.createOrEditLabel">Create or edit a OstaliPodaci</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ostaliPodaciEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="ostali-podaci-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ostali-podaci-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="ostaloLabel" for="ostali-podaci-ostalo">
                    <Translate contentKey="evaluacijaApp.ostaliPodaci.ostalo">Ostalo</Translate>
                  </Label>
                  <AvField id="ostali-podaci-ostalo" type="text" name="ostalo" />
                </AvGroup>
                <AvGroup>
                  <Label id="datumLabel" for="ostali-podaci-datum">
                    <Translate contentKey="evaluacijaApp.ostaliPodaci.datum">Datum</Translate>
                  </Label>
                  <AvInput
                    id="ostali-podaci-datum"
                    type="datetime-local"
                    className="form-control"
                    name="datum"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.ostaliPodaciEntity.datum)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ostali-podaci" replace color="info">
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
  ostaliPodaciEntity: storeState.ostaliPodaci.entity,
  loading: storeState.ostaliPodaci.loading,
  updating: storeState.ostaliPodaci.updating,
  updateSuccess: storeState.ostaliPodaci.updateSuccess
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
)(OstaliPodaciUpdate);
