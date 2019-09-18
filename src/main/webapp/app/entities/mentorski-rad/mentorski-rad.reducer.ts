import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMentorskiRad, defaultValue } from 'app/shared/model/mentorski-rad.model';

export const ACTION_TYPES = {
  FETCH_MENTORSKIRAD_LIST: 'mentorskiRad/FETCH_MENTORSKIRAD_LIST',
  FETCH_MENTORSKIRAD: 'mentorskiRad/FETCH_MENTORSKIRAD',
  CREATE_MENTORSKIRAD: 'mentorskiRad/CREATE_MENTORSKIRAD',
  UPDATE_MENTORSKIRAD: 'mentorskiRad/UPDATE_MENTORSKIRAD',
  DELETE_MENTORSKIRAD: 'mentorskiRad/DELETE_MENTORSKIRAD',
  RESET: 'mentorskiRad/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMentorskiRad>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type MentorskiRadState = Readonly<typeof initialState>;

// Reducer

export default (state: MentorskiRadState = initialState, action): MentorskiRadState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MENTORSKIRAD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MENTORSKIRAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MENTORSKIRAD):
    case REQUEST(ACTION_TYPES.UPDATE_MENTORSKIRAD):
    case REQUEST(ACTION_TYPES.DELETE_MENTORSKIRAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MENTORSKIRAD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MENTORSKIRAD):
    case FAILURE(ACTION_TYPES.CREATE_MENTORSKIRAD):
    case FAILURE(ACTION_TYPES.UPDATE_MENTORSKIRAD):
    case FAILURE(ACTION_TYPES.DELETE_MENTORSKIRAD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MENTORSKIRAD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MENTORSKIRAD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MENTORSKIRAD):
    case SUCCESS(ACTION_TYPES.UPDATE_MENTORSKIRAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MENTORSKIRAD):
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

const apiUrl = 'api/mentorski-rads';

// Actions

export const getEntities: ICrudGetAllAction<IMentorskiRad> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MENTORSKIRAD_LIST,
  payload: axios.get<IMentorskiRad>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IMentorskiRad> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MENTORSKIRAD,
    payload: axios.get<IMentorskiRad>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMentorskiRad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MENTORSKIRAD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMentorskiRad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MENTORSKIRAD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMentorskiRad> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MENTORSKIRAD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
