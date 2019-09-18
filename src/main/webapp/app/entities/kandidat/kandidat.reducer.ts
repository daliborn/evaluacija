import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IKandidat, defaultValue } from 'app/shared/model/kandidat.model';

export const ACTION_TYPES = {
  FETCH_KANDIDAT_LIST: 'kandidat/FETCH_KANDIDAT_LIST',
  FETCH_KANDIDAT: 'kandidat/FETCH_KANDIDAT',
  CREATE_KANDIDAT: 'kandidat/CREATE_KANDIDAT',
  UPDATE_KANDIDAT: 'kandidat/UPDATE_KANDIDAT',
  DELETE_KANDIDAT: 'kandidat/DELETE_KANDIDAT',
  RESET: 'kandidat/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IKandidat>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type KandidatState = Readonly<typeof initialState>;

// Reducer

export default (state: KandidatState = initialState, action): KandidatState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_KANDIDAT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_KANDIDAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_KANDIDAT):
    case REQUEST(ACTION_TYPES.UPDATE_KANDIDAT):
    case REQUEST(ACTION_TYPES.DELETE_KANDIDAT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_KANDIDAT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_KANDIDAT):
    case FAILURE(ACTION_TYPES.CREATE_KANDIDAT):
    case FAILURE(ACTION_TYPES.UPDATE_KANDIDAT):
    case FAILURE(ACTION_TYPES.DELETE_KANDIDAT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_KANDIDAT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_KANDIDAT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_KANDIDAT):
    case SUCCESS(ACTION_TYPES.UPDATE_KANDIDAT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_KANDIDAT):
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

const apiUrl = 'api/kandidats';

// Actions

export const getEntities: ICrudGetAllAction<IKandidat> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_KANDIDAT_LIST,
  payload: axios.get<IKandidat>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IKandidat> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_KANDIDAT,
    payload: axios.get<IKandidat>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IKandidat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_KANDIDAT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IKandidat> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_KANDIDAT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IKandidat> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_KANDIDAT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
