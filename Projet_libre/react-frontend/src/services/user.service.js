import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8090/';

class UserService {
  getUserBoard() {
    return axios.get(API_URL + 'user/', { headers: authHeader() });
  }
  updateUserById(userId, changes){
    console.log(changes);
    return axios.put(API_URL+"user/"+userId,changes, {headers: authHeader()});
  }

  deleteUserById(userId){
    return axios.delete(API_URL+"user/"+userId, { headers: authHeader() });
  }



}

export default new UserService();