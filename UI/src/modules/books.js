import { SUCCESS_SUFFIX } from "redux-axios-middleware";
import _omit from "lodash/omit";
import UserService from "../services/UserService";
import HttpService from "../services/HttpService";



const LIST_BOOKS = 'LIST_BOOKS';
const ADD_BOOK = 'ADD_BOOK';
const DELETE_BOOK = 'DELETE_BOOK';

export default (state = [], action) => {
  switch (action.type) {
    case LIST_BOOKS + SUCCESS_SUFFIX:
      return  action.payload.data;

    default:
      return state;
  }
};

export const allBooks = () => ({
  type: LIST_BOOKS,
  payload: {
    request: {
      url: '/demo/books',
    },
  },
});

export const addBook = book => {
  console.log(`${UserService.getUsername()} added the book ${book.title}`);
  return {
    type: ADD_BOOK,
    payload: {
      request: {
        url: '/demo/books',
        method: HttpService.HttpMethods.POST,
        data: book,
      },
      options: {
        onSuccess: ({ action, dispatch, response }) => {
          const payload = _omit(response, 'request');
          dispatch({ type: action.type + SUCCESS_SUFFIX, payload, meta: { previousAction: action } });
          dispatch(allBooks());
        },
      },
    },
  }
};

export const deleteBook = book => {
  console.log(`${UserService.getUsername()} deletes the book ${book.title}`);
  return {
    type: DELETE_BOOK,
    payload: {
      request: {
        url: `/demo/books/${book.id}`,
        method: HttpService.HttpMethods.DELETE,
      },
      options: {
        onSuccess: ({ action, dispatch, response }) => {
          const payload = _omit(response, 'request');
          dispatch({ type: action.type + SUCCESS_SUFFIX, payload, meta: { previousAction: action } });
          dispatch(allBooks());
        },
      },
    },
  }
};
