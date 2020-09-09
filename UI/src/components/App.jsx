import React from "react";
import PropTypes from 'prop-types';
import {Provider} from "react-redux";
import {Router, Route, Switch} from "react-router-dom";
import BookBox from "../components/BookBox";
import BookDetails from "../components/BookDetails";

import UserService from "../services/UserService";

import { ApolloClient, createHttpLink, InMemoryCache,ApolloProvider } from '@apollo/client';
import { setContext } from '@apollo/client/link/context';

const httpLink = createHttpLink({
  uri: '/graphql',
});

const authLink = setContext((_, { headers }) => {
  // get the authentication token from local storage if it exists
  const token = UserService.getToken();
  // return the headers to the context so httpLink can read them
  return {
    headers: {
      ...headers,
      authorization: token ? `Bearer ${token}` : "",
    }
  }
});

const client = new ApolloClient({
  link: authLink.concat(httpLink),
  cache: new InMemoryCache()
});

const App = ({ store, history }) => (
 

  <ApolloProvider client={client}>
  <Provider store={store}>
    <Router history={history}>
      <div className="container">
        <Switch>
          <Route exact path="/" component={BookBox}/>
          <Route path="/books/:bookId" component={BookDetails}/>
        </Switch>
      </div>
    </Router>
  </Provider>
  </ApolloProvider>
);

App.propTypes = {
  history: PropTypes.any.isRequired,
  store: PropTypes.any.isRequired
};

export default App;
