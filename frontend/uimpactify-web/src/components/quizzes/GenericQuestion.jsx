import React, { Component } from "react";
import "../../stylesheets/css/GenericQuestion.css";

class GenericQuestion extends Component {
  constructor(props) {
    super(props);
    // this.props.question
    // this.props.choices
    this.state = {
      selected: [],
    };
  }

  render() {
    return <div className="quiz-component">{this.props.children}</div>;
  }
}

export default GenericQuestion;
