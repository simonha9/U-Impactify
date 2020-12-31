import React, { useState, useEffect } from 'react';
import Video from 'twilio-video';
import Participant from './Participant.jsx';
import {Button, Container, Row, Col} from "shards-react";
const Room = ({ roomName, token, handleLogout }) => {
  const [room, setRoom] = useState(null);
  const [participants, setParticipants] = useState([]);
  const [vidDisabledParticipants, setVidDisabledParticipants] = useState([]);
  useEffect(() => {
    const participantConnected = participant => {
      setParticipants(prevParticipants => [...prevParticipants, participant]);
    };

    const participantDisconnected = participant => {
      setParticipants(prevParticipants =>
        prevParticipants.filter(p => p !== participant)
      );
    };
    const handleTrackDisable = (track, participants) => {
      for(const participant of participants.values()) {
        const vidTrackIter = participant.videoTracks.values().next();
        if(vidTrackIter && vidTrackIter.value.trackSid === track.sid) {
          setVidDisabledParticipants(vidDisabledParticipants + [participant.sid])
        }
      }
    }
    const handleTrackEnable = (track, participants) => {
      for(const participant of participants.values()) {
        const vidTrackIter = participant.videoTracks.values().next();
        if(vidTrackIter && vidTrackIter.value.trackSid === track.sid) {
          const filtered = vidDisabledParticipants.filter((sid)=>(sid !== participant.sid));
          setVidDisabledParticipants(filtered)
        }
      }
    }
    Video.connect(token, {
      name: roomName
    }).then(room => {
      setRoom(room);
      room.on('participantConnected', participantConnected);
      room.on('participantDisconnected', participantDisconnected);
      room.on('trackSubscribed', track => {
        track.on('enabled', () => {handleTrackEnable(track, room.participants)});
        track.on('disabled', () => {handleTrackDisable(track, room.participants)});
      });
      room.participants.forEach(participantConnected);
    });

    return () => {
      setRoom(currentRoom => {
        if (currentRoom && currentRoom.localParticipant.state === 'connected') {
          currentRoom.localParticipant.tracks.forEach(function(trackPublication) {
            trackPublication.track.stop();
          });
          currentRoom.disconnect();
          return null;
        } else {
          return currentRoom;
        }
      });
    };
  }, [roomName, token]);

  const remoteParticipants = participants.map(participant => {
    return(   
    <Col xs="6">
      <Participant key={participant.sid} participant={participant} isSelf={false} vidDisabled={vidDisabledParticipants.includes(participant.sid)}/>
    </Col>)
  }

  );

  return (
    <div className="room">
      <h2>Session: {roomName}</h2>
      <Button pill theme="danger" onClick={handleLogout}>Exit</Button>
      <div className="local-participant">

      </div>
      <div className="remote-participants">
        <Container>
          <Row>
            <Col xs="6">
                {room ? (
                <Participant
                  key={room.localParticipant.sid}
                  participant={room.localParticipant}
                  isSelf={true}
                />
                ) : (
                  ''
                )}
            </Col>
            {remoteParticipants}

          </Row>

        </Container>
        </div>
    </div>
  );
};

export default Room;