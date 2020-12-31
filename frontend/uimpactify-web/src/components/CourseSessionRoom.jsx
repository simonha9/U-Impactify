import React, { Component } from 'react';
import VideoChat from './video/VideoChat.jsx';
class CourseSessionRoom extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const storedAuthenticatedUser = JSON.parse(localStorage.getItem('authenticatedUser'));
        return(
            <VideoChat cid={this.props.cid} user={storedAuthenticatedUser} username={storedAuthenticatedUser.username} />
        );
    }

}

export default CourseSessionRoom;