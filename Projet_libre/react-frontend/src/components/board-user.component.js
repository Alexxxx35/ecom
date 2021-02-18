import React, { Component } from "react";
import userService from "../services/user.service";
import authService from "../services/auth.service";


export default class BoardUser extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: []
    };

    this.editUser= this.editUser.bind(this);
    this.deleteUser = this.deleteUser.bind(this);
  }

  editUser(id){
    this.props.history.push(`/update-user/${id}`);
  }
  deleteUser(id){
    const currentUser = authService.getCurrentUser();
    userService.deleteUserById(id).then(res =>{
      
      console.log(res.data);
      this.setState({content:this.state.content.filter(user => user.id !== id)})
    });
    if(currentUser.id == id){
      authService.logout();
      this.props.history.push('/login');
      window.location.reload();
    }
    
  }

  componentDidMount() {
    userService.getUserBoard().then(
      response => {
        this.setState({
          
          content: response.data
        });
      },
      error => {
        this.setState({
          content:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
        });
      }
    );
  }

  render() {
    return (
      <div className="container">

        <table className=" table table-striped">
          <thead>
            <tr>
              <td>ID</td>
              <td>Username</td>
              <td>Role</td>
              <td>Action</td>
              
            </tr>
          </thead>
          <tbody>
            {
              this.state.content.map(
                user=>
                <tr key  =  {user.id}>
                  <td>{user.id}</td>
                  <td>{user.username}</td>
                  <td>{user.role}</td>
                  <td>
                    <button onClick= {() => this.editUser(user.id)} className= "btn btn-info"> Update</button>
                    <button style={{marginLeft:"10px"}} onClick= {() => this.deleteUser(user.id)} className= "btn btn-danger"> Delete</button>
                  </td>
                </tr>
              )
            }
          </tbody>


        </table>






        <header className="jumbotron">
          <h3>{/*this.state.content*/}</h3>
        </header>
      </div>
    );
  }
}