import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVrstaMentorstva, defaultValue } from 'app/shared/model/vrsta-mentorstva.model';

export const ACTION_TYPES = {
  FETCH_VRSTAMENTORSTVA_LIST: 'vrstaMentorstva/FETCH_VRSTAMENTORSTVA_LIST',
  FETCH_VRSTAMENTORSTVA: 'vrstaMentorstva/FETCH_VRSTAMENTORSTVA',
  CREATE_VRSTAMENTORSTVA: 'vrstaMentorstva/CREATE_VRSTAMENTORSTVA',
  UPDATE_VRSTAMENTORSTVA: 'vrstaMentorstva/UPDATE_VRSTAMENTORSTVA',
  DELETE_VRSTAMENTORSTVA: 'vrstaMentorstva/DELETE_VRSTAMENTORSTVA',
  RESET: 'vrstaMentorstva/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVrstaMentorstva>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type VrstaMentorstvaState = Readonly<typeof initialState>;

// Reducer

export default (state: VrstaMentorstvaState = initialState, action): VrstaMentorstvaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VRSTAMENTORSTVA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VRSTAMENTORSTVA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VRSTAMENTORSTVA):
    case REQUEST(ACTION_TYPES.UPDATE_VRSTAMENTORSTVA):
    case REQUEST(ACTION_TYPES.DELETE_VRSTAMENTORSTVA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VRSTAMENTORSTVA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VRSTAMENTORSTVA):
    case FAILURE(ACTION_TYPES.CREATE_VRSTAMENTORSTVA):
    case FAILURE(ACTION_TYPES.UPDATE_VRSTAMENTORSTVA):
    case FAILURE(ACTION_TYPES.DELETE_VRSTAMENTORSTVA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VRSTAMENTORSTVA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VRSTAMENTORSTVA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VRSTAMENTORSTVA):
    case SUCCESS(ACTION_TYPES.UPDATE_VRSTAMENTORSTVA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VRSTAMENTORSTVA):
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

const apiUrl = 'api/vrsta-mentorstvas';

// Actions

export const getEntities: ICrudGetAllAction<IVrstaMentorstva> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VRSTAMENTORSTVA_LIST,
  payload: axios.get<IVrstaMentorstva>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IVrstaMentorstva> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VRSTAMENTORSTVA,
    payload: axios.get<IVrstaMentorstva>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVrstaMentorstva> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VRSTAMENTORSTVA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVrstaMentorstva> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VRSTAMENTORSTVA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVrstaMentorstva> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VRSTAMENTORSTVA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
