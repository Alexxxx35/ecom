import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import authService from "../services/auth.service";
import userService from "../services/user.service";

export default class Profile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentUser: { username: ""}
    };
  }

  componentDidMount() {
    const currentUser = authService.getCurrentUser();
    console.log(currentUser.id);
    if (!currentUser) this.setState({ redirect: "/home" });
    this.setState({ currentUser: currentUser, userReady: true })
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

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />
    }

    const { currentUser } = this.state;

    return (
      <div className="container">
        {(this.state.userReady) ?
        <div>
        <header className="jumbotron">
          <h3>
            <strong>{currentUser.username}</strong> Profil
          </h3>
        </header>
        <p>
          <strong>Token:</strong>{" "}
          {currentUser.token.substring(0, 20)} ...{" "}
          {currentUser.token.substr(currentUser.token.length - 20)}
        </p>
        <p>
          <strong>Id:</strong>{" "}
          
          {currentUser.id}
        </p>
        <strong>Authorities:</strong>
        <ul>
          {currentUser.roles }
        </ul>
      </div>: null}

      <button onClick= {() => this.editUser(currentUser.id)} className= "btn btn-info"> Update</button>
      <button style={{marginLeft:"10px"}} onClick= {() => this.deleteUser(currentUser.id)} className= "btn btn-danger"> Delete</button>
      </div>
    );
  }
}