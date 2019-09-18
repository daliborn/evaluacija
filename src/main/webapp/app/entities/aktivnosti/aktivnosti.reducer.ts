import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAktivnosti, defaultValue } from 'app/shared/model/aktivnosti.model';

export const ACTION_TYPES = {
  FETCH_AKTIVNOSTI_LIST: 'aktivnosti/FETCH_AKTIVNOSTI_LIST',
  FETCH_AKTIVNOSTI: 'aktivnosti/FETCH_AKTIVNOSTI',
  CREATE_AKTIVNOSTI: 'aktivnosti/CREATE_AKTIVNOSTI',
  UPDATE_AKTIVNOSTI: 'aktivnosti/UPDATE_AKTIVNOSTI',
  DELETE_AKTIVNOSTI: 'aktivnosti/DELETE_AKTIVNOSTI',
  RESET: 'aktivnosti/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAktivnosti>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type AktivnostiState = Readonly<typeof initialState>;

// Reducer

export default (state: AktivnostiState = initialState, action): AktivnostiState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_AKTIVNOSTI_LIST):
    case REQUEST(ACTION_TYPES.FETCH_AKTIVNOSTI):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_AKTIVNOSTI):
    case REQUEST(ACTION_TYPES.UPDATE_AKTIVNOSTI):
    case REQUEST(ACTION_TYPES.DELETE_AKTIVNOSTI):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_AKTIVNOSTI_LIST):
    case FAILURE(ACTION_TYPES.FETCH_AKTIVNOSTI):
    case FAILURE(ACTION_TYPES.CREATE_AKTIVNOSTI):
    case FAILURE(ACTION_TYPES.UPDATE_AKTIVNOSTI):
    case FAILURE(ACTION_TYPES.DELETE_AKTIVNOSTI):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_AKTIVNOSTI_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_AKTIVNOSTI):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_AKTIVNOSTI):
    case SUCCESS(ACTION_TYPES.UPDATE_AKTIVNOSTI):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_AKTIVNOSTI):
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

const apiUrl = 'api/aktivnostis';

// Actions

export const getEntities: ICrudGetAllAction<IAktivnosti> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_AKTIVNOSTI_LIST,
  payload: axios.get<IAktivnosti>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IAktivnosti> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_AKTIVNOSTI,
    payload: axios.get<IAktivnosti>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAktivnosti> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_AKTIVNOSTI,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAktivnosti> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_AKTIVNOSTI,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAktivnosti> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_AKTIVNOSTI,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
