import React from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import {
  getEvents,
  createEvent,
  deleteEvent,
  updateEvent,
} from "../helpers/services/event-service";
import { Form, FormGroup, FormInput, ButtonGroup, Button } from "shards-react";
import "../stylesheets/css/Calendar.css";

const localizer = momentLocalizer(moment);

const getAndConvertEvents = (cb) => {
  const storedAuthenticatedUserId = localStorage.getItem("authenticatedUserId");
  getEvents(storedAuthenticatedUserId).then((result) => {
    cb(
      result.data.map((x) => {
        const convertUTCDateToLocalDate = (date) => {
          let newDate = new Date(
            date.getTime() + date.getTimezoneOffset() * 60 * 1000
          );

          let offset = date.getTimezoneOffset() / 60;
          let hours = date.getHours();

          newDate.setHours(hours - offset);

          return newDate;
        };
        return {
          title: x.eventName,
          start: convertUTCDateToLocalDate(new Date(x.eventStartDate)),
          end: convertUTCDateToLocalDate(new Date(x.eventEndDate)),
          resource: { desc: x.eventDesc, id: x.id },
        };
      })
    );
  });
};

class CalendarPlanner extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      events: [],
      startDate: null,
      startTime: null,
      endDate: null,
      endTime: null,
      title: null,
      description: null,
      editMode: false,
      selectedEvent: null,
    };
  }
  componentDidMount() {
    getAndConvertEvents((x) => {
      this.setState({
        events: x,
      });
    });
  }
  render() {
    return (
      <div className="calender-group">
        <Calendar
          localizer={localizer}
          events={this.state.events}
          startAccessor="start"
          endAccessor="end"
          allDay={false}
          selectable={true}
          style={{ height: 500 }}
          onSelectEvent={(event) => {
            const eventId = event.resource.id;
            if (eventId == this.state.selectedEvent) {
              this.setState({
                startDate: "",
                startTime: "",
                endDate: "",
                endTime: "",
                title: "",
                description: "",
                selectedEvent: "",
              });
            } else {
              let localDateList = event.start.toLocaleDateString().split("/");
              const startDate =
                localDateList[0] +
                "-" +
                localDateList[1] +
                "-" +
                localDateList[2];
              const startTime = event.start.toLocaleTimeString("en-US", {
                hour12: false,
              });
              localDateList = event.end.toLocaleDateString().split("/");
              const endDate =
                localDateList[0] +
                "-" +
                localDateList[1] +
                "-" +
                localDateList[2];
              const endTime = event.end.toLocaleTimeString("en-US", {
                hour12: false,
              });
              const title = event.title;
              const description = event.resource.desc;
              this.setState({
                startDate: startDate,
                startTime: startTime,
                endDate: endDate,
                endTime: endTime,
                title: title,
                description: description,
                selectedEvent: eventId,
              });
            }
          }}
          onSelectSlot={(slot) => {
            const localDateList = slot.start.toLocaleDateString().split("/");
            const startDate =
              localDateList[0] +
              "-" +
              localDateList[1] +
              "-" +
              localDateList[2];
            this.setState({ startDate: startDate, startTime: null });
          }}
        />
        <div className="event-details-display">
          <Form>
            <h3>Event details</h3>
            <FormGroup className="calender-form-group">
              <label htmlFor="event-start-date-input">Start date</label>
              <FormInput
                disabled={!this.state.editMode}
                id="event-start-date-input"
                placeholder="yyyy-mm-dd"
                value={this.state.startDate}
                onChange={(event) => {
                  this.setState({ startDate: event.target.value });
                }}
              />
              <label htmlFor="event-start-time-input">Start time</label>
              <FormInput
                disabled={!this.state.editMode}
                id="event-start-time-input"
                placeholder="hh:mm:ss"
                value={this.state.startTime}
                onChange={(event) => {
                  this.setState({ startTime: event.target.value });
                }}
              />
            </FormGroup>
            <FormGroup className="calender-form-group">
              <label htmlFor="event-end-date-input">End date</label>
              <FormInput
                disabled={!this.state.editMode}
                id="event-end-date-input"
                placeholder="yyyy-mm-dd"
                value={this.state.endDate}
                onChange={(event) => {
                  this.setState({ endDate: event.target.value });
                }}
              />
              <label htmlFor="event-end-time-input">End date</label>
              <FormInput
                disabled={!this.state.editMode}
                id="event-end-time-input"
                placeholder="hh:mm:ss"
                value={this.state.endTime}
                onChange={(event) => {
                  this.setState({ endTime: event.target.value });
                }}
              />
            </FormGroup>
            <FormGroup>
              <label htmlFor="event-title">Title</label>
              <FormInput
                disabled={!this.state.editMode}
                id="event-title"
                placeholder="Nothing yet!"
                value={this.state.title}
                onChange={(event) => {
                  this.setState({ title: event.target.value });
                }}
              />
            </FormGroup>
            <FormGroup>
              <label htmlFor="event-description">Description</label>
              <FormInput
                disabled={!this.state.editMode}
                id="event-description"
                placeholder="Nothing yet!"
                value={this.state.description}
                onChange={(event) => {
                  this.setState({ description: event.target.value });
                }}
              />
            </FormGroup>
          </Form>
          <ButtonGroup>
            <Button
              onClick={() => {
                this.setState({ editMode: !this.state.editMode });
              }}
            >
              {this.state.editMode ? "Disable edit Mode" : "Enable edit Mode"}
            </Button>
            {this.state.editMode && !this.state.selectedEvent ? (
              <Button
                onClick={() => {
                  createEvent(
                    this.state.title,
                    this.state.description,
                    this.state.startDate,
                    this.state.startTime,
                    this.state.endDate,
                    this.state.endTime
                  ).then((res) => {
                    getAndConvertEvents((x) => {
                      this.setState({
                        events: x,
                      });
                    });
                  });
                }}
              >
                Add
              </Button>
            ) : null}
            {this.state.editMode && this.state.selectedEvent ? (
              <Button
                onClick={() => {
                  updateEvent(
                    this.state.selectedEvent,
                    this.state.title,
                    this.state.description,
                    this.state.startDate,
                    this.state.startTime,
                    this.state.endDate,
                    this.state.endTime
                  ).then((res) => {
                    getAndConvertEvents((x) => {
                      this.setState({
                        events: x,
                      });
                    });
                  });
                }}
              >
                Update
              </Button>
            ) : null}
            {this.state.editMode && this.state.selectedEvent ? (
              <Button
                onClick={() => {
                  deleteEvent(this.state.selectedEvent).then((res) => {
                    getAndConvertEvents((x) => {
                      this.setState({
                        events: x,
                      });
                    });
                    this.setState({
                      startDate: "",
                      startTime: "",
                      endDate: "",
                      endTime: "",
                      title: "",
                      description: "",
                      selectedEvent: "",
                    });
                  });
                }}
              >
                Remove
              </Button>
            ) : null}
          </ButtonGroup>
          <h4>
            {this.state.selectedEvent
              ? `Selected "${this.state.title}"`
              : "No event selected"}
          </h4>
        </div>
      </div>
    );
  }
}
export default CalendarPlanner;
