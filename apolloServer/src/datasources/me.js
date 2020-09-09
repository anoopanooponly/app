const { RESTDataSource, RequestOptions } = require('apollo-datasource-rest');

class UserAPI extends RESTDataSource {
  constructor() {
    super();
    this.baseURL = 'http://localhost:9091/api/';
   
  }
  willSendRequest(request) {
    request.headers.set('Authorization', this.context.auth);
  }
  // leaving this inside the class to make the class easier to test
  launchReducer(user) {

    return {
      name: user.principal,
      customerId: user.customerId,
      id: user.id,
      email: user.email
    };
  }

  
  async getUser() {
    console.log('this.context', this.context.auth);
    const res = await this.get('me');
    console.log('getUser', res);
    return this.launchReducer(res);
  }

}

module.exports = UserAPI;
