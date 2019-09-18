import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProjekat, defaultValue } from 'app/shared/model/projekat.model';

export const ACTION_TYPES = {
  FETCH_PROJEKAT_LIST: 'projekat/FETCH_PROJEKAT_LIST',
  FETCH_PROJEKAT: 'projekat/FETCH_PROJEKAT',
  CREATE_PROJEKAT: 'projekat/CREATE_PROJEKAT',
  UPDATE_PROJEKAT: 'projekat/UPDATE_PROJEKAT',
  DELETE_PROJEKAT: 'projekat/DELETE_PROJEKAT',
  RESET: 'projekat/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProjekat>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ProjekatState = Readonly<typeof initialState>;

// Reducer

export default (state: ProjekatState = initialState, action): ProjekatState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROJEKAT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROJEKAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROJEKAT):
    case REQUEST(ACTION_TYPES.UPDATE_PROJEKAT):
    case REQUEST(ACTION_TYPES.DELETE_PROJEKAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROJEKAT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROJEKAT):
    case FAILURE(ACTION_TYPES.CREATE_PROJEKAT):
    case FAILURE(ACTION_TYPES.UPDATE_PROJEKAT):
    case FAILURE(ACTION_TYPES.DELETE_PROJEKAT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJEKAT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJEKAT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROJEKAT):
    case SUCCESS(ACTION_TYPES.UPDATE_PROJEKAT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROJEKAT):
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

const apiUrl = 'api/projekats';

// Actions

export const getEntities: ICrudGetAllAction<IProjekat> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROJEKAT_LIST,
  payload: axios.get<IProjekat>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IProjekat> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROJEKAT,
    payload: axios.get<IProjekat>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProjekat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROJEKAT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProjekat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROJEKAT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProjekat> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROJEKAT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
