import React from "react";
import { Container, Row, Col } from "shards-react";
import EventItem from "./EventItem.jsx";

class YourCourses extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <div className="dash-component">
        <Container id="courses">
          <Row className="item-row">
            {this.props.courses.slice(0, 4).map((x) => {
              return (
                <Col>
                  <EventItem
                    image={x.image}
                    title={x.title}
                    onClick={() => {
                      window.location.href = "/courses/mycourses/" + x.id;
                    }}
                    omittime
                  />
                </Col>
              );
            })}
            <Col>
              <EventItem
                onClick={() => {
                  window.location.href = "/courses/mycourses";
                }}
                className="add-new-courses"
                image="https://image.flaticon.com/icons/png/512/32/32339.png"
                title="Add a New Course"
                omittime
              />
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}
export default YourCourses;
