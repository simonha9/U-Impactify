import React from "react";
import '../stylesheets/css/SignIn.css';
import {Container, Row, Col,
    Card,
    CardTitle,
    CardSubtitle,
    CardBody,
    CardFooter,
    Button,
    FormInput,
    FormGroup
  } from "shards-react";
import {signIn} from "../helpers/services/user-service";
import  {UserContextConsumer} from "../components/UserContextProvider";

class SignIn extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      username: "",
      password: "",
      isError: false
    }
  }

  finishSignIn = (signInCallBack) => {
    const {username, password} = this.state;
    signIn(username, password).then(
      (resp) => {
        signInCallBack(resp.data);
        this.props.history.push('/dashboard');
      },
      (error) => {
        this.setState({isError: true});
      }
    )
  }

  handleChangeUsername = (event) => {
    this.setState({username: event.target.value, isError: false});
  }
  handleChangePassword = (event) => {
    this.setState({password: event.target.value, isError: false});
  }

  render() {
    const {username, password} = this.state;
    const isFormValid = username && password;
    return (
        <UserContextConsumer>
            {(context) => (
                
                <Container 
                className = "signin-container" 
                >
                        <Card id="signin-card">
                        <CardBody>
                            <CardTitle>Sign In</CardTitle>
                            <CardSubtitle>Please enter your credentials</CardSubtitle>
                            <Col>
                                <Row>
                                <Col>
                                    <FormGroup>
                                    <label>Username</label>
                                    <FormInput placeholder="Username" onChange={this.handleChangeUsername}/>    
                                    </FormGroup>   
                                </Col>
                                </Row>
                                <Row>
                                <Col>
                                    <FormGroup>
                                    <label>Password</label>
                                    <FormInput type="password" placeholder="Password" onChange={this.handleChangePassword}/>    
                                    </FormGroup>   
                                </Col>
                                </Row>
                              {this.state.isError && <Row>
                               <p className="error">An error occured while signing in, please check your username and password</p>
                               </Row>}
                            </Col>
                        </CardBody>
                        <CardFooter>
                            <Row>
                                <Col className="container">
                                    <Button disabled={!isFormValid} onClick={()=>{this.finishSignIn(context.signIn)}}>Sign In</Button>                        
                                </Col>
                            </Row>
                        </CardFooter>
                        </Card>
                </Container>
            )}
        </UserContextConsumer>

    );
  }
}
export default SignIn;