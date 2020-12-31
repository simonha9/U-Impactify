import React, { useState, useCallback } from 'react';
import Lobby from './Lobby.jsx';
import Room from './Room.jsx';
import {getAccessToken} from '../../helpers/services/twilio-service';

const VideoChat = ({cid, user}) => {
  const [roomName, setRoomName] = useState('');
  const [token, setToken] = useState(null);



  const handleRoomNameChange = useCallback(roomName => {
    setRoomName(roomName);
  }, []);

  const handleSubmit = () => {
    setToken(getAccessToken(user.username));
  }

  const handleLogout = useCallback(event => {
    setToken(null);
  }, []);

  let render;
  if (token) {
    render = (
      <Room roomName={roomName} token={token} handleLogout={handleLogout} />
    );
  } else {
    render = (
      <Lobby
        cid = {cid}
        user={user}
        username={user.username}
        roomName={roomName}
        handleRoomNameChange={handleRoomNameChange}
        handleSubmit={handleSubmit}
      />
    );
  }
  return render;
};

export default VideoChat;