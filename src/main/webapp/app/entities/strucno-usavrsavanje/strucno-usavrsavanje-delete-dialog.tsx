import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStrucnoUsavrsavanje } from 'app/shared/model/strucno-usavrsavanje.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './strucno-usavrsavanje.reducer';

export interface IStrucnoUsavrsavanjeDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StrucnoUsavrsavanjeDeleteDialog extends React.Component<IStrucnoUsavrsavanjeDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.strucnoUsavrsavanjeEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { strucnoUsavrsavanjeEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="evaluacijaApp.strucnoUsavrsavanje.delete.question">
          <Translate contentKey="evaluacijaApp.strucnoUsavrsavanje.delete.question" interpolate={{ id: strucnoUsavrsavanjeEntity.id }}>
            Are you sure you want to delete this StrucnoUsavrsavanje?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-strucnoUsavrsavanje" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ strucnoUsavrsavanje }: IRootState) => ({
  strucnoUsavrsavanjeEntity: strucnoUsavrsavanje.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StrucnoUsavrsavanjeDeleteDialog);
