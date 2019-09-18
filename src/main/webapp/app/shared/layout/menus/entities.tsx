import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/nastavnik">
      <Translate contentKey="global.menu.entities.nastavnik" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/zavrsni-rad">
      <Translate contentKey="global.menu.entities.zavrsniRad" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/institucija">
      <Translate contentKey="global.menu.entities.institucija" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/projekat">
      <Translate contentKey="global.menu.entities.projekat" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/mentorski-rad">
      <Translate contentKey="global.menu.entities.mentorskiRad" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/aktivnosti">
      <Translate contentKey="global.menu.entities.aktivnosti" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/ostali-podaci">
      <Translate contentKey="global.menu.entities.ostaliPodaci" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/naucne-oblasti">
      <Translate contentKey="global.menu.entities.naucneOblasti" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/kandidat">
      <Translate contentKey="global.menu.entities.kandidat" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/vrsta-mentorstva">
      <Translate contentKey="global.menu.entities.vrstaMentorstva" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/strucno-usavrsavanje">
      <Translate contentKey="global.menu.entities.strucnoUsavrsavanje" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
