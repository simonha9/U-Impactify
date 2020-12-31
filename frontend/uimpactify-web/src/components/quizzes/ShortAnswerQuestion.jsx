import React, { Component } from "react";
import { createQuiz } from "../../helpers/services/quizzes-service";
import GenericQuestion from "./GenericQuestion";
import { Container, Row, Col, FormTextarea } from "shards-react";

class ShortAnswerQuestion extends Component {
  constructor(props) {
    super(props);
    // this.props.question
    // this.props.choices
    this.state = {
      selected: [],
    };
  }

  render() {
    return       <GenericQuestion>
    <div>
        <h4>{this.props.quiz.index + 1}.</h4>
        <Container>
          <Col>
            <Row>
            <FormTextarea
              className="question-editable"
              placeholder="Enter your question here"
              onChange={(event) => {
                this.props.onQuestionEdit(
                  this.props.quiz.index,
                  event.target.value
                );
              }}
              value={this.props.quiz.question}
            />   
            </Row>
            <Row>
            <FormTextarea
              className="question-editable"
              placeholder="Enter your answer here"
              onChange={(event) => {
                this.props.onAnswerEdit(
                  this.props.quiz.index,
                  event.target.value
                );
              }}
              value={this.props.quiz.solution}
              />
            </Row>
          </Col>
        </Container>
     


      <a
        className="remove-question"
        onClick={() => {
          this.props.onQuestionRemove(this.props.quiz.index);
        }}
      >
        ❌
      </a>
    </div>
  </GenericQuestion>
  }
}

export default ShortAnswerQuestion;
