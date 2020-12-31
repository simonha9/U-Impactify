import React, {useState,useEffect } from 'react';
import { FormInput, Button,Row,Col,ListGroup,ListGroupItem, ListGroupItemHeading } from 'shards-react';
import DatePicker from 'react-datepicker';
import {createCourseLiveSession, getCourseSessions, deleteSession} from '../../helpers/services/course-service';
import moment from 'moment';
const Lobby = ({
  user,
  cid,
  roomName,
  handleRoomNameChange,
  handleSubmit
}) => {
  const [sessionList, setSessionList] = useState([]);
  const [creationOpen, setCreationOpen] = useState(false);
  const [newSessionName, setNewSessionName] = useState('');
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [newSessionTime, setNewSessionTime] = useState('');
  const [newSessionDesc, setNewSessionDesc] = useState('');
  const handleCreate = () => {
    if(!creationOpen) {
      setCreationOpen(true);
    } else {
      const startTime = moment.utc(startDate).subtract(5, "hours").toISOString();
      const endTime = moment.utc(endDate).subtract(5, "hours").toISOString();
      createCourseLiveSession(cid, startTime, endTime).then(
        () => {
          fetchSessions(cid);
          setNewSessionName('');
          setNewSessionTime('');
          setNewSessionDesc('');
          setCreationOpen(false);
        }
      )
      setSessionList([...sessionList, {name: newSessionName, time: newSessionTime, description: newSessionDesc}]);

    }
  }
  const deleteCourseSession = (sessionId) => {
    deleteSession(sessionId).then(
      () => {
        fetchSessions(cid);
      }
    )
  }
  const selectRoom = (name) => {
    handleRoomNameChange(name);
    handleSubmit();
  }
  const fetchSessions = (cid) => {
    getCourseSessions(cid).then(
      (res) => {
        setSessionList(res.data);
      }
    )
  }
  useEffect(() => {
    fetchSessions(cid);
  }, []);
  const isInstructor = user.role.name === "IMPACT_CONSULTANT";
  const sessionItems = sessionList.map((session) =>{
    const startMoment = moment(session.startDate).utcOffset("-05:00");
    const endMoment = moment(session.endDate).utcOffset("-05:00");

    return(
    <ListGroupItem key={session.id}>
      <Row>
        <Col xs="2">
          <Button pill theme="success" size="small" onClick={()=>{selectRoom(session.id)}}>
            Enter
          </Button>
        </Col>
        <Col xs="2">
        {session.id}
        </Col>
        <Col xs="3">
        {startMoment.format("dddd h:mm a")}
        </Col>
        <Col xs="3">
        {endMoment.format("dddd h:mm a")}
        </Col>
        <Col xs="2">
        {isInstructor &&<Button pill theme="danger" size="small" onClick={()=>{deleteCourseSession(session.id)}}>
            Delete
          </Button>}
        </Col>
      </Row>

  </ListGroupItem>)})
  return (
    <ListGroup>
          <ListGroupItemHeading>
      <Row>
        <Col xs="2">
        
        </Col>
        <Col xs="2">
        Session Name/Id
        </Col>
        <Col xs="3">
        Start Time
        </Col>
        <Col xs="3">
        End Time
        </Col>
      </Row>

    </ListGroupItemHeading>
          {sessionItems}
          <ListGroupItem key="create">
      <Row>
        <Col xs="2">
          {isInstructor && <Button pill theme="light" size="small" onClick={handleCreate}>
            {creationOpen ? "Confirm" : "Create"}
          </Button>}
        </Col>
        <Col xs="2">
        </Col>
        <Col xs="3">
          {creationOpen &&     
          <DatePicker
            selected={startDate}
            onChange={date => setStartDate(date)}
            showTimeSelect
            dateFormat="EEE h:mm aa"
          />}

        </Col>
        <Col xs="3">
        {creationOpen &&     
          <DatePicker
            selected={endDate}
            onChange={date => setEndDate(date)}
            showTimeSelect
            dateFormat="EEE h:mm aa"
          />}        </Col>
      </Row>

    </ListGroupItem>
    </ListGroup>
  );
};

export default Lobby;