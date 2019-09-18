import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INastavnik, defaultValue } from 'app/shared/model/nastavnik.model';

export const ACTION_TYPES = {
  FETCH_NASTAVNIK_LIST: 'nastavnik/FETCH_NASTAVNIK_LIST',
  FETCH_NASTAVNIK: 'nastavnik/FETCH_NASTAVNIK',
  CREATE_NASTAVNIK: 'nastavnik/CREATE_NASTAVNIK',
  UPDATE_NASTAVNIK: 'nastavnik/UPDATE_NASTAVNIK',
  DELETE_NASTAVNIK: 'nastavnik/DELETE_NASTAVNIK',
  SET_BLOB: 'nastavnik/SET_BLOB',
  RESET: 'nastavnik/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INastavnik>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NastavnikState = Readonly<typeof initialState>;

// Reducer

export default (state: NastavnikState = initialState, action): NastavnikState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NASTAVNIK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NASTAVNIK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NASTAVNIK):
    case REQUEST(ACTION_TYPES.UPDATE_NASTAVNIK):
    case REQUEST(ACTION_TYPES.DELETE_NASTAVNIK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NASTAVNIK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NASTAVNIK):
    case FAILURE(ACTION_TYPES.CREATE_NASTAVNIK):
    case FAILURE(ACTION_TYPES.UPDATE_NASTAVNIK):
    case FAILURE(ACTION_TYPES.DELETE_NASTAVNIK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NASTAVNIK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NASTAVNIK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NASTAVNIK):
    case SUCCESS(ACTION_TYPES.UPDATE_NASTAVNIK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NASTAVNIK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/nastavniks';

// Actions

export const getEntities: ICrudGetAllAction<INastavnik> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NASTAVNIK_LIST,
  payload: axios.get<INastavnik>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INastavnik> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NASTAVNIK,
    payload: axios.get<INastavnik>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INastavnik> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NASTAVNIK,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INastavnik> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NASTAVNIK,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INastavnik> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NASTAVNIK,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
