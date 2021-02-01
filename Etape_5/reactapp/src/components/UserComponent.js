import React from 'react';
import UserService from '../services/UserService';

class UserComponent extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            users: []
        }
    }

    componentDidMount() {
        UserService.getUsers().then((res) => {
            this.setState({ users: res.data });
        })
    }

    render() {
        return (
            <div>
                <h1 className="text-center"> Users List</h1>
                <div className="row">
                    <table className="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <td> User id</td>
                                <td> User addresses</td>
                                <td> User username</td>
                                <td> User role</td>
                                <td> User creationDate</td>
                                <td> User updatedDate</td>
                            </tr>
                        </thead>

                        <tbody>
                            {
                                this.state.users.map(
                                    user =>
                                        <tr key={user.id}>
                                            <td> User id</td>
                                            <td> User addresses</td>
                                            <td> User username</td>
                                            <td> User role</td>
                                            <td> User creationDate</td>
                                            <td> User updatedDate</td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default UserComponent