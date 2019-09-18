import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStrucnoUsavrsavanje, defaultValue } from 'app/shared/model/strucno-usavrsavanje.model';

export const ACTION_TYPES = {
  FETCH_STRUCNOUSAVRSAVANJE_LIST: 'strucnoUsavrsavanje/FETCH_STRUCNOUSAVRSAVANJE_LIST',
  FETCH_STRUCNOUSAVRSAVANJE: 'strucnoUsavrsavanje/FETCH_STRUCNOUSAVRSAVANJE',
  CREATE_STRUCNOUSAVRSAVANJE: 'strucnoUsavrsavanje/CREATE_STRUCNOUSAVRSAVANJE',
  UPDATE_STRUCNOUSAVRSAVANJE: 'strucnoUsavrsavanje/UPDATE_STRUCNOUSAVRSAVANJE',
  DELETE_STRUCNOUSAVRSAVANJE: 'strucnoUsavrsavanje/DELETE_STRUCNOUSAVRSAVANJE',
  RESET: 'strucnoUsavrsavanje/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStrucnoUsavrsavanje>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type StrucnoUsavrsavanjeState = Readonly<typeof initialState>;

// Reducer

export default (state: StrucnoUsavrsavanjeState = initialState, action): StrucnoUsavrsavanjeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_STRUCNOUSAVRSAVANJE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STRUCNOUSAVRSAVANJE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_STRUCNOUSAVRSAVANJE):
    case REQUEST(ACTION_TYPES.UPDATE_STRUCNOUSAVRSAVANJE):
    case REQUEST(ACTION_TYPES.DELETE_STRUCNOUSAVRSAVANJE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_STRUCNOUSAVRSAVANJE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STRUCNOUSAVRSAVANJE):
    case FAILURE(ACTION_TYPES.CREATE_STRUCNOUSAVRSAVANJE):
    case FAILURE(ACTION_TYPES.UPDATE_STRUCNOUSAVRSAVANJE):
    case FAILURE(ACTION_TYPES.DELETE_STRUCNOUSAVRSAVANJE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_STRUCNOUSAVRSAVANJE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_STRUCNOUSAVRSAVANJE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_STRUCNOUSAVRSAVANJE):
    case SUCCESS(ACTION_TYPES.UPDATE_STRUCNOUSAVRSAVANJE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_STRUCNOUSAVRSAVANJE):
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

const apiUrl = 'api/strucno-usavrsavanjes';

// Actions

export const getEntities: ICrudGetAllAction<IStrucnoUsavrsavanje> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_STRUCNOUSAVRSAVANJE_LIST,
  payload: axios.get<IStrucnoUsavrsavanje>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IStrucnoUsavrsavanje> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STRUCNOUSAVRSAVANJE,
    payload: axios.get<IStrucnoUsavrsavanje>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IStrucnoUsavrsavanje> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STRUCNOUSAVRSAVANJE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStrucnoUsavrsavanje> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STRUCNOUSAVRSAVANJE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStrucnoUsavrsavanje> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STRUCNOUSAVRSAVANJE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
