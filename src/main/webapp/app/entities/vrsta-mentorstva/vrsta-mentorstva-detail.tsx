import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vrsta-mentorstva.reducer';
import { IVrstaMentorstva } from 'app/shared/model/vrsta-mentorstva.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVrstaMentorstvaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VrstaMentorstvaDetail extends React.Component<IVrstaMentorstvaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vrstaMentorstvaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.vrstaMentorstva.detail.title">VrstaMentorstva</Translate> [
            <b>{vrstaMentorstvaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="tip">
                <Translate contentKey="evaluacijaApp.vrstaMentorstva.tip">Tip</Translate>
              </span>
            </dt>
            <dd>{vrstaMentorstvaEntity.tip}</dd>
          </dl>
          <Button tag={Link} to="/entity/vrsta-mentorstva" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/vrsta-mentorstva/${vrstaMentorstvaEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ vrstaMentorstva }: IRootState) => ({
  vrstaMentorstvaEntity: vrstaMentorstva.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VrstaMentorstvaDetail);
