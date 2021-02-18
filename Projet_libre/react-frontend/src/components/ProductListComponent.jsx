import React, { Component } from 'react';
import productService from "../services/product.service";


class ProductListComponent extends Component {

    constructor(props){
        super(props);
        this.state = {
            productList:[]
        };

    }
    
    componentDidMount(){
        productService.getProducts().then(response => {
            console.log(response.data);
            this.setState({productList :response.data});
        });
    }

    render() {

        const { productList} = this.state;
        return (
            <div className="container">
                <h2> Produits alimentaires</h2>
                <ul> 
                    {productList && 
                    productList.map((item, index) =>
                        <div key={index} className="container"> 
                            <li >{item.nomProduit + " | Prix :" + item.prix + " €"}</li> 
                        </div> )}
                </ul>

            </div>
        );
    }
}

export default ProductListComponent;