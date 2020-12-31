import React from 'react'
import { Redirect, Route } from 'react-router-dom'
import  {UserContextConsumer} from "../components/UserContextProvider";

const PrivateRoute = ({ component: Component, ...rest }) => {
  return (
      <UserContextConsumer>
          {context => (
            <Route
            {...rest}
            render={props =>
            context.userId ? (
                <Component {...props} uid={context.userId} uinfo={context.user} />
            ) : (
                <Redirect to={{ pathname: '/', state: { from: props.location } }} />
            )
            }
            />
          )}
      </UserContextConsumer>

  )
}

export default PrivateRoute