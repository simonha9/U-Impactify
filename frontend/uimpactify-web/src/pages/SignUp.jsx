import React from "react";
import '../stylesheets/css/SignUp.css';
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
import accountTypeSocialInit from "../img/account-type-social-initiative.png";
import accountTypeStudent from "../img/account-type-student.png";
import accountTypeInstructor from "../img/account-type-instructor.png";
import checkMark from "../img/check-mark.png";
import {signUp} from "../helpers/services/user-service";

class SignUp extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      signedUp: false,
      signUpStep: 1,
      accountType: "",
      firstName: "",
      lastName: "",
      age: "",
      username: "",
      password: "",
      confirmPassword: "",
      socialInitOrg: ""
    }
  }

  movePrevPage = () => {
    this.setState({signUpStep: this.state.signUpStep-1});
  }

  moveNextPage = () => {
    this.setState({signUpStep: this.state.signUpStep+1});
  }

  finishSignUp = () => {
    const {firstName, lastName, username, age, confirmPassword, socialInitOrg, accountType} = this.state;

    signUp(firstName, lastName, username, confirmPassword, age, accountType, socialInitOrg).then(
      (resp) => {
        console.log(resp);
        this.setState({signedUp: true});
      },
      (error) => {

      }
    )
  }

  handleChangeFirstName = (event) => {
    this.setState({firstName: event.target.value});
  }
  handleChangeLastName = (event) => {
    this.setState({lastName: event.target.value});
  }
  handleChangeUsername = (event) => {
    this.setState({username: event.target.value});
  }
  handleChangeAge = (event) => {
    const re = /^[1-9]\d*$/;
    var enteredAge = event.target.value;
    if(enteredAge !== "" && !re.test(enteredAge)) {
      this.setState({age: ""});
    } else {
      this.setState({age: enteredAge});
    }
  }
  handleChangeSocialInitOrg = (event) => {
    this.setState({socialInitOrg: event.target.value});
  }
  handleChangePassword = (event) => {
    this.setState({password: event.target.value});
  }
  handleChangeConfirmPassword = (event) => {
    this.setState({confirmPassword: event.target.value});
  }

  render() {
    const {firstName, lastName, username, age, password, confirmPassword} = this.state;
    const passwordsMatch = password === confirmPassword;
    const isFormValid = firstName && lastName && username && age && password && confirmPassword && passwordsMatch && (this.state.accountType !== "social_initiative" || this.state.socialInitOrg);
    return (
        <Container 
        className = "signup-container" 
        >
                <Card id="signup-card">
                <CardBody>
                    <CardTitle>
                      Sign up
                    </CardTitle>
                    <CardSubtitle>
                      {!this.state.signedUp && this.state.signUpStep===1 && "Account Type"}
                      {!this.state.signedUp && this.state.signUpStep===2 && "Account Information"}
                      {this.state.signedUp && "Finish"}
                    </CardSubtitle>

                    {!this.state.signedUp && this.state.signUpStep ===1 && 
                    <Row >
                      <Col className = {this.state.accountType === "social_initiative" ? "accountTypeColSelected" : "accountTypeCol" } onClick={()=>{this.setState({accountType:"social_initiative"})}}>
                        <a href="#social-initiative">
                          <img src={accountTypeSocialInit} className="accountTypeImg" alt="social-initiative"></img>
                          <p>Social Initiative</p>
                        </a>
                      </Col>
                      <Col className = {this.state.accountType === "impact_learner" ? "accountTypeColSelected" : "accountTypeCol" } onClick={()=>{this.setState({accountType:"impact_learner"})}}>
                        <a href="#student">
                          <img src={accountTypeStudent} className="accountTypeImg" alt="student"></img>
                          <p>Impact Learner</p>
                        </a>
                      </Col>          
                      <Col className = {this.state.accountType === "impact_consultant" ? "accountTypeColSelected" : "accountTypeCol" } onClick={()=>{this.setState({accountType:"impact_consultant"})}}>
                        <a href="#instructor">
                          <img src={accountTypeInstructor} className="accountTypeImg" alt="instructor"></img>
                          <p>Impact Consultant</p>
                        </a>
                      </Col>
                    </Row>}
                    {!this.state.signedUp && this.state.signUpStep ===2 &&
                    <Col>
                      <Row>
                        <Col>
                          <FormGroup>
                            <label>First Name</label>
                            <FormInput placeholder="First Name" onChange={this.handleChangeFirstName} />    
                          </FormGroup>                  
                        </Col>
                        <Col>   
                          <FormGroup>
                            <label>Last Name</label>
                            <FormInput placeholder="Last Name" onChange={this.handleChangeLastName} />    
                          </FormGroup>   
                        </Col>
                      </Row>
                      <Row>
                        <Col sm="9">
                          <FormGroup>
                            <label>Username</label>
                            <FormInput placeholder="Username" onChange={this.handleChangeUsername}/>    
                          </FormGroup>   
                        </Col>
                        <Col sm="3">
                          <FormGroup>
                            <label>Age</label>
                            <FormInput type="number"  min="1" step="1" placeholder="Age" onChange={this.handleChangeAge} value={this.state.age}/>    
                          </FormGroup>   
                        </Col>
                      </Row>
                      <Row>
                        <Col>
                          <FormGroup>
                            <label>{"Related Social Initiative" + (this.state.accountType !== "social_initiative" ? "(Optional)" : "")}</label>
                            <FormInput placeholder="Org Name" onChange={this.handleChangeSocialInitOrg}/>    
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
                      <Row>
                        <Col>
                          <FormGroup>
                            <label>Confirm Password</label>
                            <FormInput type="password" placeholder="Confirm Password" onChange={this.handleChangeConfirmPassword}/>
                            {!passwordsMatch && <p className="error">Passwords not matching</p>}
                          </FormGroup>   
                        </Col>
                      </Row>
                    </Col>
                    }
                    { this.state.signedUp &&
                      <Container  >
                        <Col >
                          <Row className="signup-container">
                          <img src={checkMark} id="checkMarkImg" alt="check-mark"></img>
                          </Row>
                          <hr></hr>
                          <Row>
                            Registered successfully, you can now sign in with your new account!
                          </Row>
                        </Col>
                      </Container>
                    }
                </CardBody>
                <CardFooter>
                    <Row>
                      <Col>
                        {!this.state.signedUp && this.state.signUpStep > 1 && 
                          <Button onClick={this.movePrevPage}>&larr; Back</Button>
                        }
                      </Col>
                      <Col>
                        {!this.state.signedUp && this.state.signUpStep === 1 && 
                          <Button disabled={!this.state.accountType} onClick={this.moveNextPage}>Proceed &rarr;</Button>
                        }
                        {!this.state.signedUp && this.state.signUpStep === 2 && 
                          <Button disabled={!isFormValid} onClick={this.finishSignUp}>Proceed : Finish &rarr;</Button>                        
                        }
                      </Col>
                    </Row>
                </CardFooter>
                </Card>
        </Container>
    );
  }
}
export default SignUp;