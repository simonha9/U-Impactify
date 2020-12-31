import React, { useState, useEffect, useRef } from "react";
import {Container, Row, Col, Button} from "shards-react";
import accountTypeStudent from "../../img/account-type-student.png";
import Video from 'twilio-video';

const Participant = ({ participant, isSelf, vidDisabled }) => {
  const [videoTracks, setVideoTracks] = useState([]);
  const [audioTracks, setAudioTracks] = useState([]);
  const [isAudioEnable, setAudioEnable] = useState(true);
  const [isVideoEnable, setVideoEnable] = useState(true);
  const [isScreenEnable, setScreenEnable] = useState(false);

  const videoRef = useRef();
  const audioRef = useRef();

  const trackpubsToTracks = (trackMap) =>
    Array.from(trackMap.values())
      .map((publication) => publication.track)
      .filter((track) => track !== null);

  useEffect(() => {
    setVideoTracks(trackpubsToTracks(participant.videoTracks));
    setAudioTracks(trackpubsToTracks(participant.audioTracks));

    const trackSubscribed = (track) => {
      if (track.kind === "video") {
        setVideoTracks((videoTracks) => [...videoTracks, track]);
      } else if (track.kind === "audio") {
        setAudioTracks((audioTracks) => [...audioTracks, track]);
      }
    };

    const trackUnsubscribed = (track) => {
      if (track.kind === "video") {
        setVideoTracks((videoTracks) => videoTracks.filter((v) => v !== track));
      } else if (track.kind === "audio") {
        setAudioTracks((audioTracks) => audioTracks.filter((a) => a !== track));
      }
    };

    participant.on("trackSubscribed", trackSubscribed);
    participant.on("trackUnsubscribed", trackUnsubscribed);

    return () => {
      setVideoTracks([]);
      setAudioTracks([]);
      participant.removeAllListeners();
    };
  }, [participant]);

  useEffect(() => {
    const videoTrack = videoTracks[0];
    if (videoTrack) {
      videoTrack.attach(videoRef.current);
      //videoTrack.disable();
      //setVideoEnable(false);
      return () => {
        videoTrack.detach();
      };
    }
  }, [videoTracks]);

  useEffect(() => {
    const audioTrack = audioTracks[0];
    if (audioTrack) {
      audioTrack.attach(audioRef.current);
      //audioTrack.disable();
      //setAudioEnable(false);
      return () => {
        audioTrack.detach();
      };
    }
  }, [audioTracks]);

  const toggleAudio = () => {
    if(isAudioEnable) {
      setAudioEnable(false);
      const audioTrack = audioTracks[0];
      if(audioTrack) audioTrack.disable();
    }
    else {
      setAudioEnable(true);
      const audioTrack = audioTracks[0];
      if(audioTrack) audioTrack.enable();
    }
  }
  const toggleVideo = () => {
    if(isVideoEnable) {
      setVideoEnable(false);
      const videoTrack = videoTracks[0];
      if(videoTrack) videoTrack.disable();
    }
    else {
      setVideoEnable(true);
      const videoTrack = videoTracks[0];
      if(videoTrack) videoTrack.enable();
    }
  }

  const toggleScreen = () => {
    navigator.mediaDevices.getDisplayMedia().then(stream => {
      setScreenEnable(true);
      const screenTrack = new Video.LocalVideoTrack(stream.getTracks()[0]);
      participant.unpublishTrack(videoTracks[0]);
      participant.publishTrack(screenTrack);
      screenTrack.attach(videoRef.current);
      // room.localParticipant.publishTrack(screenTrack);
      // shareScreen.innerHTML = 'Stop sharing';
      screenTrack.mediaStreamTrack.onended = () => { 
        participant.unpublishTrack(screenTrack);
        participant.publishTrack(videoTracks[0]);
    
        screenTrack.stop();
        videoTracks[0].attach(videoRef.current);
        setScreenEnable(false);
       };
  }).catch(() => {
      alert('Could not share the screen.');
  });
  }
  return (
    <div className="participant">
      <h3>{`${participant.identity} ${isSelf ? "(You)" : ""}`}</h3>

      <video hidden={isSelf && !isVideoEnable || (!isSelf && vidDisabled)} width="100%" ref={videoRef} autoPlay={true} />
      <img hidden={isSelf && isVideoEnable || (!isSelf && !vidDisabled)} width="100%" height="262" src={accountTypeStudent}></img>
      <audio ref={audioRef} autoPlay={true} muted={false} />
      {
        isSelf && 
        <Container style={{display:"flex", justifyContent:"center"}}>
            <Button  pill theme={isVideoEnable ? "success" : "danger"} size="sm" onClick={toggleVideo}>Video</Button>

            <Button  pill theme={isAudioEnable ? "success" : "danger"} size="sm" onClick={toggleAudio}>Audio</Button>

            <Button  pill theme={isScreenEnable ? "success" : "danger"} disabled = {isScreenEnable} size="sm" onClick={toggleScreen}>Screen</Button>

          </Container>


      }

    </div>
  );
};

export default Participant;