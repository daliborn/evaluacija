import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './institucija.reducer';
import { IInstitucija } from 'app/shared/model/institucija.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInstitucijaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class InstitucijaDetail extends React.Component<IInstitucijaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { institucijaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="evaluacijaApp.institucija.detail.title">Institucija</Translate> [<b>{institucijaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="sifra">
                <Translate contentKey="evaluacijaApp.institucija.sifra">Sifra</Translate>
              </span>
            </dt>
            <dd>{institucijaEntity.sifra}</dd>
            <dt>
              <span id="naziv">
                <Translate contentKey="evaluacijaApp.institucija.naziv">Naziv</Translate>
              </span>
            </dt>
            <dd>{institucijaEntity.naziv}</dd>
            <dt>
              <span id="mjesto">
                <Translate contentKey="evaluacijaApp.institucija.mjesto">Mjesto</Translate>
              </span>
            </dt>
            <dd>{institucijaEntity.mjesto}</dd>
          </dl>
          <Button tag={Link} to="/entity/institucija" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/institucija/${institucijaEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ institucija }: IRootState) => ({
  institucijaEntity: institucija.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(InstitucijaDetail);
