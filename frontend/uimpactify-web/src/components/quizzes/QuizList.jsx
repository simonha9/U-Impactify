import React, { Component } from "react";
import { Button, Col, ListGroup, ListGroupItem, ListGroupItemHeading, Row } from "shards-react";
import {getAllQuizzesByCourseId, deleteQuiz, getQuizQAnswer} from "../../helpers/services/quizzes-service";
import TakeQuiz from "./TakeQuiz";
import moment from "moment";
class QuizList extends Component {
  constructor(props) {
    super(props);
    this.state={
        quizzes: [],
        selectedQuiz: null
    }
  }

  componentDidMount() {
      this.fetchQuizzes();
  }

  fetchQuizzes = () => {
      this.setState({quizzes:[]});
    getAllQuizzesByCourseId(this.props.cid).then(
        (res) => {
            if(this.props.user.role.name==="IMPACT_CONSULTANT") {
                this.setState({quizzes: res.data});
            } else {
                for (let i = 0; i < res.data.length; i++) {
                    let quiz = res.data[i];
                    if(quiz.quizQuestions.length > 0) {
                        getQuizQAnswer(this.props.user.id, quiz.quizQuestions[0].id).then(
                            (res)=>{
                                if(res.data.studentAnswer === "") {
                                    quiz.isTaken = false;
                                    this.setState({quizzes: this.state.quizzes.concat(quiz)})
                                } else {
                                    quiz.isTaken = true;
                                    this.setState({quizzes: this.state.quizzes.concat(quiz)})
                                }
                            },
                            ()=>{
                                quiz.isTaken = false;
                                this.setState({quizzes: this.state.quizzes.concat(quiz)})
                            }
                        )
                    } else {
                        quiz.isTaken = false;
                        this.setState({quizzes: this.state.quizzes.concat(quiz)});
                    }

                    
                }
            }

        }
    )
  }

  handleDeleteQuiz = (quizId) => {
      deleteQuiz(quizId).then(
          ()=>this.fetchQuizzes()
      )
  }

  render() {
      console.log(this.state.quizzes);
    const quizItems = this.state.quizzes.map(
        (quiz, i)=>{
            return(
            <ListGroupItem key={quiz.id}>
                <Row>
                    <Col xs="3">
                        {`Quiz #${quiz.id}`}
                    </Col>
                    <Col xs="3">
                        {moment.utc(quiz.quizStartDate).format("llll").toString()}
                    </Col>
                    <Col xs="3">
                        {moment.utc(quiz.quizEndDate).format("llll").toString()}
                    </Col>
                    <Col xs="3">
                        {this.props.user.role.name==="IMPACT_CONSULTANT" 
                        ? <Button pill theme="danger" onClick={()=>this.handleDeleteQuiz(quiz.id)}>Delete</Button>
                        : <Button pill theme="success" disabled={quiz.isTaken} onClick={()=>this.setState({selectedQuiz: quiz})}>Take Quiz</Button>}
                        
                    </Col>
                </Row>

            </ListGroupItem>
            )
        });
    return (
        <div>
            {!this.state.selectedQuiz && <ListGroupItemHeading>
                <Row>
                    <Col xs="3">
                        Quiz Name
                    </Col>
                    <Col xs="3">
                        Start time
                    </Col>
                    <Col xs="3">
                        End time
                    </Col>
                    <Col xs="3">

                    </Col>
                </Row>
            </ListGroupItemHeading>}
            {!this.state.selectedQuiz ? <ListGroup>{quizItems}</ListGroup> : <TakeQuiz uid={this.props.user.id} quiz={this.state.selectedQuiz} onSubmit={()=>{this.fetchQuizzes();this.setState({selectedQuiz: null})}}></TakeQuiz>}
        </div>
    );
  }
}

export default QuizList;