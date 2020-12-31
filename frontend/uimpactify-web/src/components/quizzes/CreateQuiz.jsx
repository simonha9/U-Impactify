import React, { Component } from "react";
import "../../stylesheets/css/CreateQuiz.css";
import { createQuiz } from "../../helpers/services/quizzes-service";
import GenericQuestion from "./GenericQuestion";
import MultipleChoiceQuestion from "./MultipleChoiceQuestion";
import ShortAnswerQuestion from "./ShortAnswerQuestion";
import {
  Button,
  Dropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
  Row,
  Col,
} from "shards-react";
import moment from 'moment';
import DatePicker from 'react-datepicker';

class CreateQuiz extends Component {
  constructor(props) {
    super(props);
    this.state = {
      quizzes: [], // each quiz has type:int, index: int, question:String, choices:[{choice:String, isCorrect:Bool}]
      open: false,
      startDate: null,
      endDate: null
    };
    this.toggle = this.toggle.bind(this);
  }

  updateQuizIndex = () => {
    this.setState(
      {
        quizzes: this.state.quizzes.map((quiz, i) => {
          let updatedQuiz = quiz;
          updatedQuiz.index = i;
          return updatedQuiz;
        }),
      },
      () => {
        console.log(this.state);
      }
    );
  };

  updateChoicesIndex = () => {
    this.setState({
      quizzes: this.state.quizzes.map((quiz) => {
        let updatedQuiz = quiz;
        updatedQuiz.choices = updatedQuiz.choices.map((choice, i) => {
          let updatedChoice = choice;
          updatedChoice.index = i;
          return updatedChoice;
        });
        return updatedQuiz;
      }),
    });
  };

  onQuestionRemove = (quizIndexToRemove) => {
    this.setState(
      {
        quizzes: this.state.quizzes.filter((quiz) => {
          return quiz.index !== quizIndexToRemove;
        }),
      },
      () => {
        this.updateQuizIndex();
      }
    );
  };

  onQuestionEdit = (quizIndexToEdit, textToChangeTo) => {
    console.log(quizIndexToEdit);
    console.log(textToChangeTo);
    console.log("------------------------");
    this.setState(
      {
        quizzes: this.state.quizzes.map((quiz) => {
          let updatedQuiz = quiz;
          if (quizIndexToEdit === quiz.index) {
            updatedQuiz.question = textToChangeTo;
            return updatedQuiz;
          }
          return updatedQuiz;
        }),
      },
      () => this.updateQuizIndex()
    );
  };

  onAnswerEdit = (quizIndexToEdit, textToChangeTo) => {
    this.setState(
      {
        quizzes: this.state.quizzes.map((quiz) => {
          let updatedQuiz = quiz;
          if (quizIndexToEdit === quiz.index) {
            updatedQuiz.solution = textToChangeTo;
            return updatedQuiz;
          }
          return updatedQuiz;
        }),
      },
      () => this.updateQuizIndex()
    );
  };

  onChoiceAdd = (quizIndexToAddChoice, addedChoice) => {
    this.setState(
      {
        quizzes: this.state.quizzes.map((quiz) => {
          if (quizIndexToAddChoice === quiz.index) {
            let updatedQuiz = quiz;
            updatedQuiz.choices.push(addedChoice);
            return updatedQuiz;
          }
          return quiz;
        }),
      },
      () => this.updateChoicesIndex()
    );
  };

  onChoiceRemove = (quizIndexToRemoveChoice, choiceIndexToRemove) => {
    this.setState(
      {
        quizzes: this.state.quizzes.map((quiz) => {
          let updatedQuiz = quiz;
          if (quizIndexToRemoveChoice === quiz.index) {
            updatedQuiz.choices = updatedQuiz.choices.filter((choice) => {
              return choice.index !== choiceIndexToRemove;
            });
          }
          return updatedQuiz;
        }),
      },
      () => this.updateChoicesIndex()
    );
  };
  handleDropdownAdd = (type) => {
    this.setState(
      (prevState) => {
        let quizzes = prevState.quizzes.concat({
          index: 0,
          question: "",
          choices: [],
          type: type,
        });
        return {
          quizzes,
        };
      },
      () => {
        this.updateQuizIndex();
      }
    );
  };

  onChoiceEdit = (
    quizIndexToRemoveChoice,
    choiceIndexToChange,
    textToChangeTo
  ) => {
    this.setState(
      {
        quizzes: this.state.quizzes.map((quiz) => {
          let updatedQuiz = quiz;
          if (quizIndexToRemoveChoice === quiz.index) {
            updatedQuiz.choices = updatedQuiz.choices.map((choice) => {
              if (choice.index === choiceIndexToChange) {
                let updatedChoice = choice;
                updatedChoice.choice = textToChangeTo;
                updatedQuiz.solution = updatedChoice.choice;
                return updatedChoice;
              }
              return choice;
            });
          }
          return updatedQuiz;
        }),
      },
      () => this.updateChoicesIndex()
    );
  };

  onChoiceSelectTrueFalse = (quizIndexToChange, choiceIndexToChange) => {
    this.setState(
      {
        quizzes: this.state.quizzes.map((quiz) => {
          let updatedQuiz = quiz;
          if (quizIndexToChange === quiz.index) {
            updatedQuiz.choices = updatedQuiz.choices.map((choice) => {
              if (choice.index === choiceIndexToChange) {
                let updatedChoice = choice;
                updatedChoice.isCorrect = !updatedChoice.isCorrect;
                return updatedChoice;
              }
              return choice;
            });
          }
          return updatedQuiz;
        }),
      },
      () => this.updateChoicesIndex()
    );
  };

  handleDropdownAdd = (type) => {
    this.setState(
      (prevState) => {
        let quizzes = prevState.quizzes.concat({
          index: 0,
          question: "",
          choices: [],
          type: type,
        });
        return {
          quizzes,
        };
      },
      () => {
        this.updateQuizIndex();
      }
    );
  };

  handleSubmit = () => {
    const quizQuestions = this.state.quizzes.map(
      (quiz)=>{
        console.log(quiz);
        if(quiz.type == "MULTIPLE_CHOICE") {
          const quizQuestionOptions = quiz.choices.map(
            (choice) => {
              return {
                questionOption: choice.choice
              }
            }
          )
          return {
            questionType: quiz.type,
            question: quiz.question,
            solution:{answer:quiz.solution},
            questionOptions: quizQuestionOptions
          }
        } else if (quiz.type == "SHORT_ANSWER") {
          return {
            questionType: quiz.type,
            question: quiz.question,
            solution:{answer:quiz.solution},
          }
        }
      });
    const startTime = moment.utc(this.state.startDate).subtract(5, "hours").toISOString();
    const endTime = moment.utc(this.state.endDate).subtract(5, "hours").toISOString();
    console.log(startTime);
    createQuiz(this.props.cid, startTime, endTime, quizQuestions).then(
      (res) => {
        this.setState({quizzes: []});
        this.props.onCreate();
        console.log(res);
      }
    );
  };
  
  toggle = () => {
    this.setState((prevState) => {
      return { open: !prevState.open };
    });
  };

  render = () => {
    return (
      <div id="quiz-creator">
        <Row>
          <Col xs="4">
          Quiz start time
            <DatePicker
              selected={this.state.startDate}
              onChange={date => this.setState({startDate: date})}
              showTimeSelect
              dateFormat="MMMM d, h:mm aa"
            />
          </Col>
          <Col xs="4">
          Quiz end time
            <DatePicker
              selected={this.state.endDate}
              onChange={date => this.setState({endDate: date})}
              showTimeSelect
              dateFormat="MMMM d, h:mm aa"
            />
          </Col>
        </Row>

        {this.state.quizzes.map((quiz) => {
          if (quiz.type == "MULTIPLE_CHOICE") {
            return (
              <MultipleChoiceQuestion
                quiz={quiz}
                onQuestionEdit={this.onQuestionEdit}
                onQuestionRemove={this.onQuestionRemove}
                onChoiceAdd={this.onChoiceAdd}
                onChoiceRemove={this.onChoiceRemove}
                onChoiceEdit={this.onChoiceEdit}
                onChoiceSelectTrueFalse={this.onChoiceSelectTrueFalse}
              />
            );
          } else if (quiz.type == "SHORT_ANSWER") {
            return <ShortAnswerQuestion 
                    quiz={quiz} 
                    onQuestionEdit={this.onQuestionEdit}
                    onQuestionRemove={this.onQuestionRemove}
                    onAnswerEdit={this.onAnswerEdit}
                    />;
          }
        })}
        <div id="quiz-type-dropdown">
          <h3>Add a...</h3>
          <Dropdown open={this.state.open} toggle={this.toggle}>
            <DropdownToggle>Question</DropdownToggle>
            <DropdownMenu>
              <DropdownItem onClick={() => this.handleDropdownAdd("MULTIPLE_CHOICE")}>
                Multiple choice
              </DropdownItem>
              <DropdownItem onClick={() => this.handleDropdownAdd("SHORT_ANSWER")}>
                Short Answer
              </DropdownItem>
            </DropdownMenu>
          </Dropdown>
        </div>
        <Button id="quiz-submit" onClick={this.handleSubmit}>Done, submit</Button>
      </div>
    );
  };
}

export default CreateQuiz;
