import React, { Component } from 'react';
import authService from '../services/auth.service';
import userService from '../services/user.service';




class UpdateUserComponent extends Component {
    constructor(props){
        super(props)
        this.state={
            id: this.props.match.params.id,
            newUsername: ''
        }

        this.onChangeUsername = this.onChangeUsername.bind(this);
    }

    onChangeUsername(e) {
        this.setState({
          newUsername: e.target.value
        });
      }
    updateUser = (e) =>{
        const currentUser = authService.getCurrentUser();
        e.preventDefault(); 
        let changes = {username : this.state.newUsername}
        userService.updateUserById(this.state.id,changes).then (res =>{
            if(currentUser.id == this.state.id){
                authService.logout();
                this.props.history.push('/login');
                window.location.reload();
            }
            else{
                this.props.history.push('/home');
            }
        });
        
        
    }
    render() {
        return (
        <div className="col-md-12">
            <div className= "card col-md-6 offset-mc-3">
            <div className="form-group">
              <label htmlFor="newUsername"> New Username</label>
              <input name= "newUsername" className = "form-control"
                value= {this.state.newUsername} onChange={this.onChangeUsername}/>
            </div>

            <div className="form-group">
              <button className="btn btn-primary btn-block" onClick={this.updateUser}>
                <span>Update</span>
              </button>
            </div>
            </div>
      </div>
        );
    }
}

export default UpdateUserComponent;