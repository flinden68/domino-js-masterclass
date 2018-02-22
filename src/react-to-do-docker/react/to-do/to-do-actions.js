import tempData from '../temp-data-store/temp-data';
import globals from '../globals';
import { postPendingData } from '../core/core-logic';
import axios from 'axios';

/*
  ENUMS
*/
export const actions = {
  RESET_STATE:'RESET_TODO_STATE',
  CREATE_DOCUMENT: 'CREATE_TODO_PROFILE',
  EDIT_DOCUMENT: 'EDIT_TODO_PROFILE',
  DELETE_DOCUMENT: 'DELETE_TODO_PROFILE',
  CANCEL_DOCUMENT: 'CANCEL_TODO_PROFILE',
  SAVE_DOCUMENT: 'SAVE_TODO_PROFILE',
  FILTER_LIST: 'FILTER_TODO_LIST',
  FETCH_ALL_DATA: 'FETCH_ALL_TODO_DATA',
  UPDATE_DATA: 'UPDATE_TODO_DATA'
}

/*
  QUICK STATE FUNCTIONS
*/
export const resetState = () => {
  return {
    type: actions.RESET_STATE
  }
}

export const createProfile = () => {
  return {
    type: actions.CREATE_DOCUMENT
  }
}

export const editProfile = (id) => {
  return {
    type: actions.EDIT_DOCUMENT,
    id
  }
}

export const cancelProfile = () => {
  return {
    type: actions.CANCEL_DOCUMENT
  }
}

export const filterList = (key, value) => {
  return {
    type: actions.FILTER_LIST,
    key,
    value
  }
}

export const updateData = (data) => {
  return {
    type: actions.UPDATE_DATA,
    data
  }
}

/*
  ADVANCED STATE FUNCTIONS
*/
export const submitProfile = (state) => {
  return dispatch => {
    let tempArray = state.data.concat();
    let tempIndex = 0;
    let activeEntry = tempData.toDo.activeEntry;
    var entry = {};
    var dupArray = null;
    var reqType = "";

    //Finalise formatting of data
    activeEntry.data.taskName = _.trim(activeEntry.data.taskName);
    activeEntry.data.description = _.trim(activeEntry.data.description);

    //Change status of record to Pending
    tempData.toDo.activeEntry.custom.status = 'warning';

    if(activeEntry.custom.isNewDoc){
      //Add new Entry to State
      activeEntry.custom.isNewDoc = false;
      activeEntry.custom.action = "create";
      tempArray.push(activeEntry);

      //Add Entry to Queue
      tempData.toDo.data.push(JSON.parse(JSON.stringify(activeEntry)));
    }else{
      if(activeEntry.custom.isSavedDoc){
        activeEntry.custom.action = "update";
      }else{
        activeEntry.custom.action = "create";
      }

      //Update Entry in State
      tempIndex = tempArray.findIndex(t => t._id === activeEntry._id);
      tempArray[tempIndex] = activeEntry;

      //Add Entry to queue, or update existing entry in queue
      tempIndex = tempData.toDo.data.findIndex(t => t._id === activeEntry._id);

      if(tempIndex > -1){
        //Update the existing entry
        tempData.toDo.data[tempIndex] = activeEntry;
      }else{
        //Add Entry to queue
        tempData.toDo.data.push(JSON.parse(JSON.stringify(activeEntry)));
      }
    }

    dispatch({
      type: actions.SAVE_DOCUMENT,
      tempArray
    })

    dupArray = JSON.parse(JSON.stringify(tempData.toDo.data));

    for(var x in dupArray){
      entry = dupArray[x];
      reqType = entry.custom.action === "create" ? "post" : "put";

      postPendingData("to-do", entry, reqType)
      .then(function (response) {
        if(response.data.success){
          response.data.data.custom.action = "";
          response.data.data.custom.isSavedDoc = true;
          response.data.data.custom.status = "";

          dispatch(updateData(response.data.data));
        }
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });
    }

    dupArray = null;
    tempData.toDo.data = [];
  }
}

export const deleteProfile = (id, state) => {
  return dispatch => {
    let tempEntry = {};
    let tempIndex = tempData.toDo.data.findIndex(t => t._id === id);
    let entry = {};
    let dupArray = null;

    if(tempIndex > -1){
      tempEntry = tempData.toDo.data[tempIndex];

      if(tempEntry.custom.isSavedDoc){
        //Change the action type of the entry to be "delete"
        tempEntry.custom.action = "delete";
      }else{
        //Remove the entry
        tempData.toDo.data.splice(tempIndex, 1);
      }
    }else{
      tempEntry = state.data.find(t => t._id === id);

      if(tempEntry){
        tempEntry.custom.action = "delete";
        tempData.toDo.data.push(JSON.parse(JSON.stringify(tempEntry)));
      }
    }

    dispatch({
      type: actions.DELETE_DOCUMENT,
      id
    })

    dupArray = JSON.parse(JSON.stringify(tempData.toDo.data));

    for(var x in dupArray){
      entry = dupArray[x];

      postPendingData("to-do", entry, "delete")
      .then(function (response) {
        if(response.data.success){
          entry.custom.action = "";
          entry.custom.status = "";

          dispatch(updateData(entry));
        }
      })
      .catch(function (error) {
        console.log(error);
        return null;
      });
    }

    dupArray = null;
    tempData.toDo.data = [];
  }
}

export const fetchAllData = () => {
  return dispatch => {
    loadDataExtended(dispatch);
  };
}

export const loadDataExtended = (dispatch) => {
  let config = {headers: {"api-key": localStorage.getItem("token"), "Content-Type": "application/json"}};
  let url = `${globals.apiUrl}/todo/data`;

  axios.get(url, config)
  .then(function (response) {
    //We need to add custom object to each record
    for(var x in response.data.data){
      response.data.data[x].custom = JSON.parse(JSON.stringify(tempData.toDo.dataTemplate.custom));
      response.data.data[x].custom.isSavedDoc = true;
      response.data.data[x].custom.isNewDoc = false;
      response.data.data[x].custom.status = ""
    }

    dispatch({type: actions.FETCH_ALL_DATA, payload:response.data.data});
  })
  .catch(function (error) {
    console.log(error);
  });

  return null;
}