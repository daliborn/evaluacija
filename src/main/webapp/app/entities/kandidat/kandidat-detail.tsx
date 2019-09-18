import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './kandidat.reducer';
import { IKandidat } from 'app/shared/model/kandidat.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IKandidatDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class KandidatDetail extends React.Component<IKandidatDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { kandidatEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.kandidat.detail.title">Kandidat</Translate> [<b>{kandidatEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="ime">
                <Translate contentKey="evaluacijaApp.kandidat.ime">Ime</Translate>
              </span>
            </dt>
            <dd>{kandidatEntity.ime}</dd>
            <dt>
              <span id="prezime">
                <Translate contentKey="evaluacijaApp.kandidat.prezime">Prezime</Translate>
              </span>
            </dt>
            <dd>{kandidatEntity.prezime}</dd>
            <dt>
              <span id="jmbg">
                <Translate contentKey="evaluacijaApp.kandidat.jmbg">Jmbg</Translate>
              </span>
            </dt>
            <dd>{kandidatEntity.jmbg}</dd>
          </dl>
          <Button tag={Link} to="/entity/kandidat" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/kandidat/${kandidatEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ kandidat }: IRootState) => ({
  kandidatEntity: kandidat.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(KandidatDetail);
