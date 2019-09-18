import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IZavrsniRad, defaultValue } from 'app/shared/model/zavrsni-rad.model';

export const ACTION_TYPES = {
  FETCH_ZAVRSNIRAD_LIST: 'zavrsniRad/FETCH_ZAVRSNIRAD_LIST',
  FETCH_ZAVRSNIRAD: 'zavrsniRad/FETCH_ZAVRSNIRAD',
  CREATE_ZAVRSNIRAD: 'zavrsniRad/CREATE_ZAVRSNIRAD',
  UPDATE_ZAVRSNIRAD: 'zavrsniRad/UPDATE_ZAVRSNIRAD',
  DELETE_ZAVRSNIRAD: 'zavrsniRad/DELETE_ZAVRSNIRAD',
  RESET: 'zavrsniRad/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IZavrsniRad>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ZavrsniRadState = Readonly<typeof initialState>;

// Reducer

export default (state: ZavrsniRadState = initialState, action): ZavrsniRadState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ZAVRSNIRAD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ZAVRSNIRAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ZAVRSNIRAD):
    case REQUEST(ACTION_TYPES.UPDATE_ZAVRSNIRAD):
    case REQUEST(ACTION_TYPES.DELETE_ZAVRSNIRAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ZAVRSNIRAD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ZAVRSNIRAD):
    case FAILURE(ACTION_TYPES.CREATE_ZAVRSNIRAD):
    case FAILURE(ACTION_TYPES.UPDATE_ZAVRSNIRAD):
    case FAILURE(ACTION_TYPES.DELETE_ZAVRSNIRAD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ZAVRSNIRAD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ZAVRSNIRAD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ZAVRSNIRAD):
    case SUCCESS(ACTION_TYPES.UPDATE_ZAVRSNIRAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ZAVRSNIRAD):
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

const apiUrl = 'api/zavrsni-rads';

// Actions

export const getEntities: ICrudGetAllAction<IZavrsniRad> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ZAVRSNIRAD_LIST,
  payload: axios.get<IZavrsniRad>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IZavrsniRad> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ZAVRSNIRAD,
    payload: axios.get<IZavrsniRad>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IZavrsniRad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ZAVRSNIRAD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IZavrsniRad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ZAVRSNIRAD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IZavrsniRad> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ZAVRSNIRAD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
