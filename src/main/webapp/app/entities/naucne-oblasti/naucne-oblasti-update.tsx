import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IZavrsniRad } from 'app/shared/model/zavrsni-rad.model';
import { getEntities as getZavrsniRads } from 'app/entities/zavrsni-rad/zavrsni-rad.reducer';
import { getEntity, updateEntity, createEntity, reset } from './naucne-oblasti.reducer';
import { INaucneOblasti } from 'app/shared/model/naucne-oblasti.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface INaucneOblastiUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface INaucneOblastiUpdateState {
  isNew: boolean;
  zavrsniRadoviId: string;
}

export class NaucneOblastiUpdate extends React.Component<INaucneOblastiUpdateProps, INaucneOblastiUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      zavrsniRadoviId: '0',
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { naucneOblastiEntity } = this.props;
      const entity = {
        ...naucneOblastiEntity,
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
    this.props.history.push('/entity/naucne-oblasti');
  };

  render() {
    const { naucneOblastiEntity, zavrsniRads, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="evaluacijaApp.naucneOblasti.home.createOrEditLabel">
              <Translate contentKey="evaluacijaApp.naucneOblasti.home.createOrEditLabel">Create or edit a NaucneOblasti</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : naucneOblastiEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="naucne-oblasti-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="naucne-oblasti-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="oblastLabel" for="naucne-oblasti-oblast">
                    <Translate contentKey="evaluacijaApp.naucneOblasti.oblast">Oblast</Translate>
                  </Label>
                  <AvField id="naucne-oblasti-oblast" type="text" name="oblast" />
                </AvGroup>
                <AvGroup>
                  <Label for="naucne-oblasti-zavrsniRadovi">
                    <Translate contentKey="evaluacijaApp.naucneOblasti.zavrsniRadovi">Zavrsni Radovi</Translate>
                  </Label>
                  <AvInput id="naucne-oblasti-zavrsniRadovi" type="select" className="form-control" name="zavrsniRadovi.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/naucne-oblasti" replace color="info">
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
  naucneOblastiEntity: storeState.naucneOblasti.entity,
  loading: storeState.naucneOblasti.loading,
  updating: storeState.naucneOblasti.updating,
  updateSuccess: storeState.naucneOblasti.updateSuccess
});

const mapDispatchToProps = {
  getZavrsniRads,
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
)(NaucneOblastiUpdate);
