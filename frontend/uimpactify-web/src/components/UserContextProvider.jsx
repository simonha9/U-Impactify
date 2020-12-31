import React, { Component } from "react";
import {getUser} from "../helpers/services/user-service";
const { Provider, Consumer } = React.createContext();

class UserContextProvider extends Component {
  constructor(props) {
    super(props)
    const storedAuthenticatedUserId = localStorage.getItem('authenticatedUserId');
    const storedAuthenticatedUser = JSON.parse(localStorage.getItem('authenticatedUser'));
    this.state = {
      userId: storedAuthenticatedUserId,
      user: storedAuthenticatedUser,
    };
  }
  signOut = () => {
    localStorage.removeItem('authenticatedUserId');
    localStorage.removeItem('authenticatedUser');
    this.setState({userId: null, user: null})
  }
  signIn = (authenticatedUserId) => {
    localStorage.setItem('authenticatedUserId', authenticatedUserId);   
    this.setState({userId:authenticatedUserId})
    getUser(authenticatedUserId).then(
      (resp) => {
        localStorage.setItem('authenticatedUser', JSON.stringify(resp.data));
        const storedAuthenticatedUser = JSON.parse(localStorage.getItem('authenticatedUser'));
        console.log(storedAuthenticatedUser);   
        this.setState({user:resp.data})
      }
    )

  }
  render() {
    return <Provider value={{userId: this.state.userId, user: this.state.user, signOut: this.signOut, signIn: this.signIn}}>{this.props.children}</Provider>;
  }
}

export { UserContextProvider, Consumer as UserContextConsumer };