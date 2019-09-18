import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOstaliPodaci, defaultValue } from 'app/shared/model/ostali-podaci.model';

export const ACTION_TYPES = {
  FETCH_OSTALIPODACI_LIST: 'ostaliPodaci/FETCH_OSTALIPODACI_LIST',
  FETCH_OSTALIPODACI: 'ostaliPodaci/FETCH_OSTALIPODACI',
  CREATE_OSTALIPODACI: 'ostaliPodaci/CREATE_OSTALIPODACI',
  UPDATE_OSTALIPODACI: 'ostaliPodaci/UPDATE_OSTALIPODACI',
  DELETE_OSTALIPODACI: 'ostaliPodaci/DELETE_OSTALIPODACI',
  RESET: 'ostaliPodaci/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOstaliPodaci>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type OstaliPodaciState = Readonly<typeof initialState>;

// Reducer

export default (state: OstaliPodaciState = initialState, action): OstaliPodaciState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OSTALIPODACI_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OSTALIPODACI):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_OSTALIPODACI):
    case REQUEST(ACTION_TYPES.UPDATE_OSTALIPODACI):
    case REQUEST(ACTION_TYPES.DELETE_OSTALIPODACI):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_OSTALIPODACI_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OSTALIPODACI):
    case FAILURE(ACTION_TYPES.CREATE_OSTALIPODACI):
    case FAILURE(ACTION_TYPES.UPDATE_OSTALIPODACI):
    case FAILURE(ACTION_TYPES.DELETE_OSTALIPODACI):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_OSTALIPODACI_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_OSTALIPODACI):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_OSTALIPODACI):
    case SUCCESS(ACTION_TYPES.UPDATE_OSTALIPODACI):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_OSTALIPODACI):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/ostali-podacis';

// Actions

export const getEntities: ICrudGetAllAction<IOstaliPodaci> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_OSTALIPODACI_LIST,
  payload: axios.get<IOstaliPodaci>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IOstaliPodaci> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OSTALIPODACI,
    payload: axios.get<IOstaliPodaci>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IOstaliPodaci> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OSTALIPODACI,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOstaliPodaci> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OSTALIPODACI,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOstaliPodaci> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OSTALIPODACI,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
