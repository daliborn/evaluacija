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
import { getEntity, updateEntity, createEntity, reset } from './aktivnosti.reducer';
import { IAktivnosti } from 'app/shared/model/aktivnosti.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAktivnostiUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAktivnostiUpdateState {
  isNew: boolean;
  nastavniciId: string;
}

export class AktivnostiUpdate extends React.Component<IAktivnostiUpdateProps, IAktivnostiUpdateState> {
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
    if (errors.length === 0) {
      const { aktivnostiEntity } = this.props;
      const entity = {
        ...aktivnostiEntity,
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
    this.props.history.push('/entity/aktivnosti');
  };

  render() {
    const { aktivnostiEntity, nastavniks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.aktivnosti.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.aktivnosti.home.createOrEditLabel">Create or edit a Aktivnosti</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : aktivnostiEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="aktivnosti-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="aktivnosti-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="brojCitataLabel" for="aktivnosti-brojCitata">
                    <Translate contentKey="evaluacijaApp.aktivnosti.brojCitata">Broj Citata</Translate>
                  </Label>
                  <AvField id="aktivnosti-brojCitata" type="string" className="form-control" name="brojCitata" />
                </AvGroup>
                <AvGroup>
                  <Label id="brojRadovaLabel" for="aktivnosti-brojRadova">
                    <Translate contentKey="evaluacijaApp.aktivnosti.brojRadova">Broj Radova</Translate>
                  </Label>
                  <AvField id="aktivnosti-brojRadova" type="string" className="form-control" name="brojRadova" />
                </AvGroup>
                <AvGroup>
                  <Label id="brojDomaciProjekataLabel" for="aktivnosti-brojDomaciProjekata">
                    <Translate contentKey="evaluacijaApp.aktivnosti.brojDomaciProjekata">Broj Domaci Projekata</Translate>
                  </Label>
                  <AvField id="aktivnosti-brojDomaciProjekata" type="string" className="form-control" name="brojDomaciProjekata" />
                </AvGroup>
                <AvGroup>
                  <Label id="brojMedjunarodnihProjekataLabel" for="aktivnosti-brojMedjunarodnihProjekata">
                    <Translate contentKey="evaluacijaApp.aktivnosti.brojMedjunarodnihProjekata">Broj Medjunarodnih Projekata</Translate>
                  </Label>
                  <AvField
                    id="aktivnosti-brojMedjunarodnihProjekata"
                    type="string"
                    className="form-control"
                    name="brojMedjunarodnihProjekata"
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/aktivnosti" replace color="info">
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
  aktivnostiEntity: storeState.aktivnosti.entity,
  loading: storeState.aktivnosti.loading,
  updating: storeState.aktivnosti.updating,
  updateSuccess: storeState.aktivnosti.updateSuccess
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
)(AktivnostiUpdate);
