import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMentorskiRad } from 'app/shared/model/mentorski-rad.model';
import { getEntities as getMentorskiRads } from 'app/entities/mentorski-rad/mentorski-rad.reducer';
import { getEntity, updateEntity, createEntity, reset } from './kandidat.reducer';
import { IKandidat } from 'app/shared/model/kandidat.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IKandidatUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IKandidatUpdateState {
  isNew: boolean;
  diplomeId: string;
}

export class KandidatUpdate extends React.Component<IKandidatUpdateProps, IKandidatUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      diplomeId: '0',
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

    this.props.getMentorskiRads();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { kandidatEntity } = this.props;
      const entity = {
        ...kandidatEntity,
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
    this.props.history.push('/entity/kandidat');
  };

  render() {
    const { kandidatEntity, mentorskiRads, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.kandidat.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.kandidat.home.createOrEditLabel">Create or edit a Kandidat</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : kandidatEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="kandidat-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="kandidat-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="imeLabel" for="kandidat-ime">
                    <Translate contentKey="evaluacijaApp.kandidat.ime">Ime</Translate>
                  </Label>
                  <AvField
                    id="kandidat-ime"
                    type="text"
                    name="ime"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="prezimeLabel" for="kandidat-prezime">
                    <Translate contentKey="evaluacijaApp.kandidat.prezime">Prezime</Translate>
                  </Label>
                  <AvField
                    id="kandidat-prezime"
                    type="text"
                    name="prezime"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="jmbgLabel" for="kandidat-jmbg">
                    <Translate contentKey="evaluacijaApp.kandidat.jmbg">Jmbg</Translate>
                  </Label>
                  <AvField id="kandidat-jmbg" type="text" name="jmbg" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/kandidat" replace color="info">
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
  mentorskiRads: storeState.mentorskiRad.entities,
  kandidatEntity: storeState.kandidat.entity,
  loading: storeState.kandidat.loading,
  updating: storeState.kandidat.updating,
  updateSuccess: storeState.kandidat.updateSuccess
});

const mapDispatchToProps = {
  getMentorskiRads,
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
)(KandidatUpdate);
