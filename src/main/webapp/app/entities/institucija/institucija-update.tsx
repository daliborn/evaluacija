import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './institucija.reducer';
import { IInstitucija } from 'app/shared/model/institucija.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInstitucijaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IInstitucijaUpdateState {
  isNew: boolean;
}

export class InstitucijaUpdate extends React.Component<IInstitucijaUpdateProps, IInstitucijaUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { institucijaEntity } = this.props;
      const entity = {
        ...institucijaEntity,
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
    this.props.history.push('/entity/institucija');
  };

  render() {
    const { institucijaEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.institucija.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.institucija.home.createOrEditLabel">Create or edit a Institucija</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : institucijaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="institucija-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="institucija-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="sifraLabel" for="institucija-sifra">
                    <Translate contentKey="evaluacijaApp.institucija.sifra">Sifra</Translate>
                  </Label>
                  <AvField id="institucija-sifra" type="text" name="sifra" />
                </AvGroup>
                <AvGroup>
                  <Label id="nazivLabel" for="institucija-naziv">
                    <Translate contentKey="evaluacijaApp.institucija.naziv">Naziv</Translate>
                  </Label>
                  <AvField id="institucija-naziv" type="text" name="naziv" />
                </AvGroup>
                <AvGroup>
                  <Label id="mjestoLabel" for="institucija-mjesto">
                    <Translate contentKey="evaluacijaApp.institucija.mjesto">Mjesto</Translate>
                  </Label>
                  <AvField id="institucija-mjesto" type="text" name="mjesto" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/institucija" replace color="info">
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
  institucijaEntity: storeState.institucija.entity,
  loading: storeState.institucija.loading,
  updating: storeState.institucija.updating,
  updateSuccess: storeState.institucija.updateSuccess
});

const mapDispatchToProps = {
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
)(InstitucijaUpdate);
