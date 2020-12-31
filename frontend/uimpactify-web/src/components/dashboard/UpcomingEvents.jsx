import React, { Fragment } from "react";
import { Container, Row, Col, Button } from "shards-react";

class UpcomingEvents extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <Container>
        <h4>Today</h4>
        <h6 id="today-event">
          {this.props.today ? this.props.today : "Nothing!"}
        </h6>
        <h4>Tomorrow</h4>
        <h6 id="tomorrow-event">
          {this.props.tomorrow ? this.props.tomorrow : "Nothing!"}
        </h6>
      </Container>
    );
  }
}
export default UpcomingEvents;
