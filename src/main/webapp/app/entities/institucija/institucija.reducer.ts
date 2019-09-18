import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInstitucija, defaultValue } from 'app/shared/model/institucija.model';

export const ACTION_TYPES = {
  FETCH_INSTITUCIJA_LIST: 'institucija/FETCH_INSTITUCIJA_LIST',
  FETCH_INSTITUCIJA: 'institucija/FETCH_INSTITUCIJA',
  CREATE_INSTITUCIJA: 'institucija/CREATE_INSTITUCIJA',
  UPDATE_INSTITUCIJA: 'institucija/UPDATE_INSTITUCIJA',
  DELETE_INSTITUCIJA: 'institucija/DELETE_INSTITUCIJA',
  RESET: 'institucija/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInstitucija>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type InstitucijaState = Readonly<typeof initialState>;

// Reducer

export default (state: InstitucijaState = initialState, action): InstitucijaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_INSTITUCIJA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INSTITUCIJA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_INSTITUCIJA):
    case REQUEST(ACTION_TYPES.UPDATE_INSTITUCIJA):
    case REQUEST(ACTION_TYPES.DELETE_INSTITUCIJA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_INSTITUCIJA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INSTITUCIJA):
    case FAILURE(ACTION_TYPES.CREATE_INSTITUCIJA):
    case FAILURE(ACTION_TYPES.UPDATE_INSTITUCIJA):
    case FAILURE(ACTION_TYPES.DELETE_INSTITUCIJA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSTITUCIJA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSTITUCIJA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_INSTITUCIJA):
    case SUCCESS(ACTION_TYPES.UPDATE_INSTITUCIJA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_INSTITUCIJA):
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

const apiUrl = 'api/institucijas';

// Actions

export const getEntities: ICrudGetAllAction<IInstitucija> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_INSTITUCIJA_LIST,
  payload: axios.get<IInstitucija>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IInstitucija> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INSTITUCIJA,
    payload: axios.get<IInstitucija>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IInstitucija> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INSTITUCIJA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInstitucija> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INSTITUCIJA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInstitucija> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INSTITUCIJA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
