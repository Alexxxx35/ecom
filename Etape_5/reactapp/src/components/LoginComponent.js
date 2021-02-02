import React, { Component } from "react";
import axios from "axios";

class login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: "username",
      password: "password"
    };
    this.handleFormSubmit = this.handleFormSubmit.bind(this);
  }

  handleFormSubmit = event => {
    event.preventDefault();

    const endpoint = "http://localhost:8090/authenticate";

    const username = this.state.username;
    const password = this.state.password;

    const user_object = {
      username: username,
      password: password
    };

    axios.post(endpoint, user_object).then(res => {
      localStorage.setItem("authorization", res.data.token);
      return this.handleDashboard();
    });
  };

  render() {
    return (
      <div>
        <div className="wrapper">
          <form className="form-signin" onSubmit={this.handleFormSubmit}>
            <h2 className="form-signin-heading">Please login</h2>
            <div className="form-group">
              <input type="text"
                className="form-control"
                placeholder="Search..."
                defaultValue=""
              />
            </div>
            <div className="form-group">
              <input type="password"
                className="form-control"
                placeholder="Search..."
                defaultValue=""
              />
            </div>
            <button className="btn btn-lg btn-primary btn-block" type="submit">
              Login
            </button>
          </form>
        </div>
      </div>
    );
  }
}
export default login;