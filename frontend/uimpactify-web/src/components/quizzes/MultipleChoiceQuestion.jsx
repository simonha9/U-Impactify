import React, { Component } from "react";
import GenericQuestion from "./GenericQuestion";
import { createQuiz } from "../../helpers/services/quizzes-service";
import { FormRadio, Button, FormTextarea } from "shards-react";

class MultipleChoiceQuestion extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <GenericQuestion>
        <div id="question-header">
          <div className="question-component-index-and-question">
            <h4>{this.props.quiz.index + 1}.</h4>
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
          </div>

          <h4
            className="remove-question"
            onClick={() => {
              this.props.onQuestionRemove(this.props.quiz.index);
            }}
          >
            ❌
          </h4>
        </div>

        {this.props.quiz.choices.map((choice, i) => {
          return (
            <div className={"question-component-choice"}>
              <div className="radio-and-choice">
                <FormRadio
                  name={"choice" + i}
                  checked={choice.isCorrect}
                  onChange={() => {
                    this.props.onChoiceSelectTrueFalse(
                      this.props.quiz.index,
                      i
                    );
                  }}
                ></FormRadio>
                <FormTextarea
                  className="choices-editable"
                  placeholder="⬅Select if choice is CORRECT, and edit your choice here"
                  onChange={(event) => {
                    this.props.onChoiceEdit(
                      this.props.quiz.index,
                      i,
                      event.target.value
                    );
                  }}
                  value={choice.choice}
                />
              </div>

              <h4
                className="remove-choice"
                onClick={() => {
                  this.props.onChoiceRemove(this.props.quiz.index, i);
                }}
              >
                ❌
              </h4>
            </div>
          );
        })}
        <Button
          className="question-component-add"
          onClick={() => {
            this.props.onChoiceAdd(this.props.quiz.index, {
              choice: "",
              isCorrect: false,
              index: 0,
            });
          }}
        >
          Add a choice
        </Button>
      </GenericQuestion>
    );
  }
}

export default MultipleChoiceQuestion;
