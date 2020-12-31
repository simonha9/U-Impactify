import React from "react";
import { Container, Row } from "shards-react";

class EventItems extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <Container
        className={`${this.props.className} event-item`}
        onClick={this.props.onClick}
      >
        <Row>
          <img
            class="event-image"
            src={
              this.props.image
                ? this.props.image
                : "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Question_mark_%28black%29.svg/1200px-Question_mark_%28black%29.svg.png"
            }
          />
        </Row>
        <Row>
          <div class="event-title">
            {this.props.title ? this.props.title : "Event"}
          </div>
        </Row>
        {this.props.omittime ? null : (
          <Row>
            <div class="event-time">
              {this.props.time ? this.props.time : "Time"}
            </div>
          </Row>
        )}
      </Container>
    );
  }
}
export default EventItems;
