import React, { Component } from 'react';
import "../stylesheets/css/AssignmentsTab.css";
import AssignmentListing from "./assignments/AssignmentListing";
import AssignmentCreate from "./assignments/AssignmentCreate";
import AssignmentDetails from "./assignments/AssignmentDetails";

class AssignmentsTab extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: "listing",
            curr_assign: null,
            cid: props.cid,
            uid: props.uid,
            role: props.role,
        }
    }

    handleBackToListing = () => {
        this.setState({ page: "listing" })
    }

    handleCreateButton = () => {
        this.setState({ page: "create" });
    }

    handleGoToDetails = (assign) => {
        this.setState({ curr_assign: assign, page: "details" });
    }

    render() {
        const { page, curr_assign, cid, uid, role } = this.state;
        if (page === "listing") {
            return <AssignmentListing cid={cid} uid={uid} role={role} 
                        onCreate={this.handleCreateButton} onGoDetails={this.handleGoToDetails}/>
        } else if (page === "create") {
            return <AssignmentCreate cid={cid} uid={uid} role={role}
                        onBack={this.handleBackToListing}/>
        } else if (page === "details") {
            return <AssignmentDetails cid={cid} uid={uid} role={role} curr_assign={curr_assign}
                        onBack={this.handleBackToListing}/>
        }
    }
}

export default AssignmentsTab;