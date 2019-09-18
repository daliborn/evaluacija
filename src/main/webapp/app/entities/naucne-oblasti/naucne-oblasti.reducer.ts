import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INaucneOblasti, defaultValue } from 'app/shared/model/naucne-oblasti.model';

export const ACTION_TYPES = {
  FETCH_NAUCNEOBLASTI_LIST: 'naucneOblasti/FETCH_NAUCNEOBLASTI_LIST',
  FETCH_NAUCNEOBLASTI: 'naucneOblasti/FETCH_NAUCNEOBLASTI',
  CREATE_NAUCNEOBLASTI: 'naucneOblasti/CREATE_NAUCNEOBLASTI',
  UPDATE_NAUCNEOBLASTI: 'naucneOblasti/UPDATE_NAUCNEOBLASTI',
  DELETE_NAUCNEOBLASTI: 'naucneOblasti/DELETE_NAUCNEOBLASTI',
  RESET: 'naucneOblasti/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INaucneOblasti>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NaucneOblastiState = Readonly<typeof initialState>;

// Reducer

export default (state: NaucneOblastiState = initialState, action): NaucneOblastiState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NAUCNEOBLASTI_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NAUCNEOBLASTI):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NAUCNEOBLASTI):
    case REQUEST(ACTION_TYPES.UPDATE_NAUCNEOBLASTI):
    case REQUEST(ACTION_TYPES.DELETE_NAUCNEOBLASTI):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NAUCNEOBLASTI_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NAUCNEOBLASTI):
    case FAILURE(ACTION_TYPES.CREATE_NAUCNEOBLASTI):
    case FAILURE(ACTION_TYPES.UPDATE_NAUCNEOBLASTI):
    case FAILURE(ACTION_TYPES.DELETE_NAUCNEOBLASTI):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NAUCNEOBLASTI_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NAUCNEOBLASTI):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NAUCNEOBLASTI):
    case SUCCESS(ACTION_TYPES.UPDATE_NAUCNEOBLASTI):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NAUCNEOBLASTI):
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

const apiUrl = 'api/naucne-oblastis';

// Actions

export const getEntities: ICrudGetAllAction<INaucneOblasti> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NAUCNEOBLASTI_LIST,
  payload: axios.get<INaucneOblasti>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INaucneOblasti> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NAUCNEOBLASTI,
    payload: axios.get<INaucneOblasti>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INaucneOblasti> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NAUCNEOBLASTI,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INaucneOblasti> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NAUCNEOBLASTI,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INaucneOblasti> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NAUCNEOBLASTI,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
