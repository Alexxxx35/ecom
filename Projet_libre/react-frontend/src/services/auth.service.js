import axios from "axios";
import authHeader from './auth-header';

const API_URL = "http://localhost:8090/";

/*let axiosConfig = {
  headers: {
      'Content-Type': 'application/json',
  }
};*/
class AuthService {

  getMe(){
    return axios.get(API_URL + 'me', { headers: authHeader() });
  }
    login(username, password) {
      return axios
        .post(API_URL + "authenticate", {
          username,
          password
        })
        .then(response => {
          if (response.data.token) {
            response.data.username = username;
            console.log(JSON.stringify(response.data));
            localStorage.setItem("user", JSON.stringify(response.data));
          
          }
          return response.data;
        });
    }
    
    logout() {
      localStorage.removeItem("user");
    }
  
    register(username, password) {

      return axios.post(API_URL + "register", {
        username,
        password
      });
    }
  
    
    getCurrentUser() {

      return JSON.parse(localStorage.getItem('user'));
    }
  }
  
  export default new AuthService();