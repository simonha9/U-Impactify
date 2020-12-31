import React from "react";
import { Container, Row, Col } from "shards-react";
import EventItem from "./EventItem.jsx";

class WorkShops extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <Container id="workshops">
        <Row>
          <div>Upcoming Workshops</div>
        </Row>
        <Row className="item-row">
          {this.props.workshops.slice(0, 3).map((x) => {
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
export default WorkShops;
