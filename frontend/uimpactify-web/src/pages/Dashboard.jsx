import React, { Fragment } from "react";
import { Container, Row, Col, Button } from "shards-react";
import "../stylesheets/css/Dashboard.css";
import UpcomingEvents from "../components/dashboard/UpcomingEvents";
import SuggestedEvents from "../components/dashboard/SuggestedEvents";
import WorkShops from "../components/dashboard/WorkShops";
import YourCourses from "../components/dashboard/YourCourses";
import News from "../components/dashboard/News";
import {
  getTodayEvent,
  getTomorrowEvent,
} from "../helpers/services/event-service";
import { getStudentCourses } from "../helpers/services/course-service";
class Dashboard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      courses: [],
      today: null,
      tomorrow: null,
    };
  }
  componentDidMount() {
    let authId = localStorage.getItem("authenticatedUserId");
    getStudentCourses(authId).then((resp) => {
      this.setState({
        courses: resp.data.map((x) => {
          return {
            title: x.course.courseName,
            image: "/static/media/knowledge_clipart.e38938d0.png",
            id: x.course.id,
          };
        }),
      });
    });
    getTodayEvent(authId).then((resp) => {
      let firstEvent = resp.data[0];
      if (firstEvent) {
        let firstEventName = firstEvent.eventName;
        let firstEventTime = `${
          firstEvent.eventStartDate.split("T")[1].split(":")[0]
        }:${firstEvent.eventStartDate.split("T")[1].split(":")[1]}`;
        this.setState({
          today: `${firstEventName} starting ${firstEventTime}`,
        });
      }
    });
    getTomorrowEvent(authId).then((resp) => {
      let firstEvent = resp.data[0];
      if (firstEvent) {
        let firstEventName = firstEvent.eventName;
        let firstEventTime = `${
          firstEvent.eventStartDate.split("T")[1].split(":")[0]
        }:${firstEvent.eventStartDate.split("T")[1].split(":")[1]}`;
        this.setState({
          tomorrow: `${firstEventName} starting ${firstEventTime}`,
        });
      }
    });
  }
  render() {
    return (
      <Container id="content-container" className="content-container">
        <Col className="col-1">
          <Row className="row-1-1">
            <div className="title-group">
              <h4>Upcoming Events</h4>
              <Button
                onClick={() => {
                  window.location.href = "/calendar";
                }}
              >
                Open Calendar
              </Button>
            </div>
            <div id="upcoming-events" className="dash-component">
              <UpcomingEvents
                today={this.state.today}
                tomorrow={this.state.tomorrow}
              />
            </div>
          </Row>
          <Row className="row-1-2">
            <div className="title-group">
              <h4>Your Courses</h4>
              <Button
                onClick={() => {
                  window.location.href = "/courses/mycourses";
                }}
              >
                See All
              </Button>
            </div>
            <YourCourses courses={this.state.courses} />
          </Row>
        </Col>
        <Col className="col-2">
          <Row className="row-1-1">
            <div className="title-group">
              <h4>Suggested Events</h4>
              <Button>See All</Button>
            </div>
            <div className="dash-component">
              <SuggestedEvents networking={[
                {image:"https://i1.wp.com/www.eatthis.com/wp-content/uploads/2019/03/old-fashioned-cocktail.jpg?resize=970%2C546&ssl=1", title:"Party1", time:"Tomorrow"},
                {image:"https://i1.wp.com/www.eatthis.com/wp-content/uploads/2019/03/old-fashioned-cocktail.jpg?resize=970%2C546&ssl=1", title:"Party2", time:"Tomorrow"},
                {image:"https://i1.wp.com/www.eatthis.com/wp-content/uploads/2019/03/old-fashioned-cocktail.jpg?resize=970%2C546&ssl=1", title:"Party3", time:"Tomorrow"}]} />
              <WorkShops workshops={[
               {image:"https://i1.wp.com/www.manae-business.fr/wp-content/uploads/2020/03/you-x-ventures-Oalh2MojUuk-unsplash.jpg?resize=2000%2C1200&ssl=1", title:"Workshop1", time:"Tomorrow"},
               {image:"https://i1.wp.com/www.manae-business.fr/wp-content/uploads/2020/03/you-x-ventures-Oalh2MojUuk-unsplash.jpg?resize=2000%2C1200&ssl=1", title:"Workshop2", time:"Tomorrow"},
               {image:"https://i1.wp.com/www.manae-business.fr/wp-content/uploads/2020/03/you-x-ventures-Oalh2MojUuk-unsplash.jpg?resize=2000%2C1200&ssl=1", title:"Workshop3", time:"Tomorrow"}]} />
            </div>
          </Row>
          <Row className="row-1-2">
            <h4>News</h4>
            <div className="dash-component news">
              <News news={[{}, {}, {}, {}]} />
            </div>
          </Row>
        </Col>
      </Container>
    );
  }
}
export default Dashboard;
