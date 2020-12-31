import React, { Component } from "react";
import { Button, Col, FormRadio, FormTextarea, ListGroup, ListGroupItem, Row } from "shards-react";
import {submitQuiz} from "../../helpers/services/quizzes-service";
class TakeQuiz extends Component {
  constructor(props) {
    super(props);
    this.state = {
        answers: {}
    }
  }

  handleSubmit = () => {
      console.log(Object.entries(this.state.answers));
      const answersToSubmit = Object.entries(this.state.answers).map(
          (pair) => ({student:{ id: this.props.uid}, question: {id: pair[0]}, studentAnswer: pair[1]})
      )
      console.log(this.state.answers);
      submitQuiz(answersToSubmit).then(
          (res) => {
              this.props.onSubmit();
              console.log(res);
          }
      )
  };

  handleAnswerUpdate = (questionId, newValue) => {
      const updatedAnswers = this.state.answers;
      updatedAnswers[questionId] = newValue;
      this.setState({answers: updatedAnswers});
  };

  render() {
      const questions = this.props.quiz.quizQuestions.map(
          (question, i)=> {
              if(question.questionType === "MULTIPLE_CHOICE") {
                const questionOptions = question.questionOptions.map(
                    (option) => (
                        <ListGroupItem key={option.id}>
                            <FormRadio 
                                checked={this.state.answers[question.id] === option.questionOption}
                                onChange={()=>this.handleAnswerUpdate(question.id, option.questionOption)}
                            >
                                {option.questionOption}
                            </FormRadio>
                        </ListGroupItem>
                    )
                )
                return  <ListGroup style={{marginBottom: 25}} key={question.id}>
                            <ListGroupItem>
                                <Col>
                                    <Row>
                                        {`Question ${i+1}: ${question.question}`}
                                    </Row>
                                </Col>
                            </ListGroupItem>
                            {questionOptions}
                        </ListGroup>
              } else if (question.questionType === "SHORT_ANSWER") {
                return  <ListGroup style={{marginBottom: 25}} key={question.id}>
                            <ListGroupItem>
                                <Col>
                                    <Row>
                                        {`Question ${i+1}: ${question.question}`}
                                    </Row>
                                </Col>
                            </ListGroupItem>
                            <ListGroupItem>
                                <Col>
                                    <Row>
                                        <FormTextarea placeholder="Write you answer here" onChange={(event)=>this.handleAnswerUpdate(question.id, event.target.value)}/>
                                    </Row>
                                </Col>
                            </ListGroupItem>
                        </ListGroup>
              }
          }
      );
    return <Col>
            <h2>{`Quiz #${this.props.quiz.id}`}</h2>
                {questions}
            <Button onClick={this.handleSubmit}>Submit</Button>
            </Col>;
  }
}

export default TakeQuiz;
