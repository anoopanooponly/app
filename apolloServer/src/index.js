require('dotenv').config();

const { ApolloServer } = require('apollo-server');
const isEmail = require('isemail');

const typeDefs = require('./schema');
const resolvers = require('./resolvers');


const LaunchAPI = require('./datasources/launch');
 const UserAPI = require('./datasources/me');

const internalEngineDemo = require('./engine-demo');


// set up any dataSources our resolvers need
const dataSources = () => ({
  launchAPI: new LaunchAPI(),
  UserAPI: new UserAPI()
});

// the function that sets up the global context for each resolver, using the req
const context = async ({ req }) => {
  // simple auth check on every request
  const auth = (req.headers && req.headers.authorization) || 'Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJPQmlWcFlKRGVsM2F3VUlCeGsxdTNET0h5OXdNMlU1WXNRUXJ3czdwa3djIn0.eyJleHAiOjE1OTk1NjU1ODYsImlhdCI6MTU5OTU2NTI4NiwiYXV0aF90aW1lIjoxNTk5NTY1Mjg1LCJqdGkiOiI0YjI3NzU4My05MmU4LTRiYTAtODJiYy03OTgxZWY3Y2RiYjciLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvZGVtbyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI3MGJiOTIzZi1jYTcwLTQ1ZWQtYjFhNy1jZjI2NjY5ZjlhOTUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJkZW1vY2xpZW50Iiwibm9uY2UiOiJmYzZmMjYzNC1mODgyLTRjOGUtODcwZC0wZDc2MjRiZDMyMjYiLCJzZXNzaW9uX3N0YXRlIjoiYmNjNzYzYWItZTIyNS00YTNjLTg1NTQtOTQyMjJmZTIyZjI0IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwidGVuYW50SWQiOiIxIiwibmFtZSI6ImFub29wIG0iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhbm9vcCIsImdpdmVuX25hbWUiOiJhbm9vcCIsImZhbWlseV9uYW1lIjoibSIsImVtYWlsIjoiYW5vb3BAZi5jb20ifQ.N-C6wHYS5X5Lm-IY0egpV4J4Txjxo6z3LtBziFaM-jBt-hPF52B0w7r1TDde1ofocfJA2a_VjHjrgxgF06CzGDmyKvYXcgNatECvuEX3sSZRjjo3Py7HuoY4XMWZ-AaKbCiCQH3UCvNRNW6n-_C7XKqVQpHfORpXj1q7xzDjdPCJeoJCkbmRtMtGh23kG20yY1YADCv51-YZ1xT713vBkpXkH8OXeTi3DkRNfGYwbVWcVo8cPXtFDmV28S0qK46-txWEk01dEu0TwYIPgzB4n1DWCtvQzj-uioi8GYJZ4I3_Ha7NN41XIdyEaELX-MPNg0fCh6iD7EVA_OVGTINkDQ';
  return { auth };
};

// Set up Apollo Server
const server = new ApolloServer({
  typeDefs,
  resolvers,
  dataSources,
  context,
  introspection: true,
  playground: true,
  cors: {
    origin: "*",
    methods: "GET,HEAD,PUT,PATCH,POST,DELETE",
    preflightContinue: false,
    optionsSuccessStatus: 204,
    credentials: true
  },
  engine: {
    apiKey: process.env.ENGINE_API_KEY,
    ...internalEngineDemo,
  },
});

// Start our server if we're not in a test env.
// if we're in a test env, we'll manually start it in a test
if (process.env.NODE_ENV !== 'test') {
  server
    .listen({ port: process.env.PORT || 4000 })
    .then(({ url }) => {
      console.log(`🚀 app running at ${url}`)
    });
}

// export all the important pieces for integration/e2e tests to use
module.exports = {
  dataSources,
  context,
  typeDefs,
  resolvers,
  ApolloServer,
  LaunchAPI,
  UserAPI,
  server,
};
