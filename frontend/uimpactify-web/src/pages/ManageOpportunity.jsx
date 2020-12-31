import React, { Component } from 'react';
import { getPosting, updatePosting, deletePosting, getApplicationsByPosting, deleteApplication } from "../helpers/services/user-service";
import "../stylesheets/css/Opportunities.css";

class ManageOpportunity extends Component {
    constructor(props) {
        super(props);
        this.state = {
            uid: props.uid,
            socialInit: props.uinfo.socialInit,
            pid: props.match.params.pid,
            type: "",
            title: "",
            description: "",
            appList: []
        }
    }

    componentDidMount() {
        const { pid } = this.state;
        getPosting(pid).then(
            (r) => {
                const title = r.data.name;
                const type = r.data.postingType;
                const description = r.data.postingDesc;
                this.setState({
                    title: title,
                    type: type,
                    description: description
                });
            }
        );

        var appList = [];
        getApplicationsByPosting(pid).then(
            (r) => {
                for (var i = 0; i < r.data.length; i++) {
                    const curr = r.data[i];
                    const name = curr.applicant.firstName + " " + curr.applicant.lastName;
                    var type = "EMPLOYEE";
                    if (curr.applicant.role) {
                        type = curr.applicant.role.name;
                    }
                    var socialInit = "None";
                    if (curr.applicant.socialInit) {
                        socialInit = curr.applicant.socialInit.name;
                    }
                    appList.push({id: curr.id, email: curr.email, name: name, type: type, socialInit: socialInit});
                }
                this.setState({ appList: appList });
            }
        );
    }

    handleSave = (event) => {
        event.preventDefault();
        const { pid, title, description, uid, type, socialInit } = this.state;
        updatePosting(pid, title, description, uid, type, socialInit.name).then(
            (r) => {
                this.props.history.push("/opportunity/myopportunities");
            }
        );
    }

    handleDelete = (event) => {
        event.preventDefault();
        deletePosting(this.state.pid).then(
            (r) => {
                this.props.history.push("/opportunity/myopportunities");
            }
        );
    }

    handleClickApp = (id) => {
        var appButton = document.getElementById(id + "_button");
        var appDetail = document.getElementById(id + "_details");
        if (appDetail.style.display === "none") {
            appDetail.style.display = "";
            appButton.className += " expanded";    
        } else {
            appDetail.style.display = "none";
            appButton.className = appButton.className.replace(" expanded", "");
        }
    }

    handleRejectApp = (id) => {
        deleteApplication(id).then(
            (_) => {
                const newAppList = this.state.appList.filter((a) => a.id !== id);
                this.setState({ appList: newAppList });
            }
        );
    }

    render() {
        const { title, description, appList } = this.state;
        var isValid = title && description;

        return (
            <div className="opportunity-page-container">
                <div className="shadow p-3 mb-5 bg-white rounded">
                    <h3 className="opportunity-page-header">Managing Opportunity</h3>
                </div>

                <div className="row">
                    <form className="col-md-6 left">
                        <div className="opportunity-formGroup">
                            <label htmlFor="title">Title</label>
                            <input 
                                type="text" 
                                className="form-control"
                                id="title" 
                                value={this.state.title}
                                onChange={(event) => this.setState({title: event.target.value})} />
                        </div>
                        <div className="opportunity-formGroup">
                            <label htmlFor="type">Type</label>
                            <input 
                                type="text" 
                                className="form-control"
                                id="type" 
                                value={this.state.type}
                                disabled />
                        </div>
                        <div className="opportunity-formGroup">
                            <label htmlFor="description">Description</label>
                            <textarea
                                type="text"
                                className="form-control"
                                id="description"
                                rows='10'
                                value={this.state.description}
                                onChange={(event) => this.setState({description: event.target.value})}/>
                        </div>
                        
                        <button type="submit" 
                                className="btn btn-primary opportunity-formButtons" 
                                disabled={!isValid}
                                onClick={(event) => this.handleSave(event)}>
                            Save
                        </button>
                        <button onClick={() => this.props.history.push("/opportunity/myopportunities")}
                           className="btn btn-secondary opportunity-formButtons">
                            Cancel
                        </button>
                        <button type="submit" 
                                className="btn btn-danger opportunity-formButtons" 
                                style={ { float: "right" } }
                                onClick={(event) => this.handleDelete(event)}>
                            Delete
                        </button>
                    </form>

                    <div className="col-md-6 right">
                        <div className="justify-content-center align-items-center">
                            <h3>Applicants</h3>
                            {appList.map((app) => (
                                <div key={app.id} className="opportunity-application">
                                    <button onClick={() => this.handleClickApp(app.id)}
                                            className="application-button"
                                            id={app.id + "_button"}>
                                        {app.email}
                                    </button>
                                    <div className="application-detail"
                                         id={app.id + "_details"}
                                         style={{ display: "none" }}>
                                        Name: &emsp;&emsp;&emsp; {app.name}<br/>
                                        Type: &emsp;&emsp;&emsp; {app.type}<br/>
                                        Association: &emsp; {app.socialInit}<br />
                                        <button className="btn btn-danger btn-sm float-right"
                                                onClick={() => this.handleRejectApp(app.id)}>
                                            Reject
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
                
            </div>
        );
    }
}

export default ManageOpportunity