import axios from 'axios';
const API_URL = 'http://localhost:8090/';

class ProductService {
  getProducts() {
    return axios.get(API_URL + 'products/');
  }
  updateProductById(productId, changes){
    console.log(changes);
    return axios.put(API_URL+"product/"+productId,changes);
  }

  deleteProductById(productId){
    return axios.delete(API_URL+"product/"+productId);
  }



}

export default new ProductService();