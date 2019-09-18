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
import { getEntity, updateEntity, createEntity, reset } from './vrsta-mentorstva.reducer';
import { IVrstaMentorstva } from 'app/shared/model/vrsta-mentorstva.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVrstaMentorstvaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVrstaMentorstvaUpdateState {
  isNew: boolean;
  diplomeId: string;
}

export class VrstaMentorstvaUpdate extends React.Component<IVrstaMentorstvaUpdateProps, IVrstaMentorstvaUpdateState> {
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
      const { vrstaMentorstvaEntity } = this.props;
      const entity = {
        ...vrstaMentorstvaEntity,
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
    this.props.history.push('/entity/vrsta-mentorstva');
  };

  render() {
    const { vrstaMentorstvaEntity, mentorskiRads, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.vrstaMentorstva.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.vrstaMentorstva.home.createOrEditLabel">Create or edit a VrstaMentorstva</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vrstaMentorstvaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="vrsta-mentorstva-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="vrsta-mentorstva-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="tipLabel" for="vrsta-mentorstva-tip">
                    <Translate contentKey="evaluacijaApp.vrstaMentorstva.tip">Tip</Translate>
                  </Label>
                  <AvField
                    id="vrsta-mentorstva-tip"
                    type="text"
                    name="tip"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/vrsta-mentorstva" replace color="info">
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
  vrstaMentorstvaEntity: storeState.vrstaMentorstva.entity,
  loading: storeState.vrstaMentorstva.loading,
  updating: storeState.vrstaMentorstva.updating,
  updateSuccess: storeState.vrstaMentorstva.updateSuccess
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
)(VrstaMentorstvaUpdate);
