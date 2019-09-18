import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './naucne-oblasti.reducer';
import { INaucneOblasti } from 'app/shared/model/naucne-oblasti.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INaucneOblastiDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NaucneOblastiDetail extends React.Component<INaucneOblastiDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { naucneOblastiEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.naucneOblasti.detail.title">NaucneOblasti</Translate> [<b>{naucneOblastiEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="oblast">
                <Translate contentKey="evaluacijaApp.naucneOblasti.oblast">Oblast</Translate>
              </span>
            </dt>
            <dd>{naucneOblastiEntity.oblast}</dd>
            <dt>
              <Translate contentKey="evaluacijaApp.naucneOblasti.zavrsniRadovi">Zavrsni Radovi</Translate>
            </dt>
            <dd>{naucneOblastiEntity.zavrsniRadovi ? naucneOblastiEntity.zavrsniRadovi.naziv : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/naucne-oblasti" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/naucne-oblasti/${naucneOblastiEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ naucneOblasti }: IRootState) => ({
  naucneOblastiEntity: naucneOblasti.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NaucneOblastiDetail);
