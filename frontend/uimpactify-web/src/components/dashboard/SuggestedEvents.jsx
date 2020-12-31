import React from "react";
import { Container, Row, Col } from "shards-react";
import EventItem from "./EventItem.jsx";

class UpcomingEvents extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <Container id="suggested-events">
        <Row>
          <div>Upcoming network Events</div>
        </Row>
        <Row className="item-row">
          {this.props.networking.slice(0, 3).map((x) => {
            return (
              <Col>
                <EventItem image={x.image} title={x.title} time={x.time} />
              </Col>
            );
          })}
        </Row>
      </Container>
    );
  }
}
export default UpcomingEvents;
