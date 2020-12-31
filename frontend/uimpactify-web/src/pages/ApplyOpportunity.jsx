import React, { Component } from 'react';
import "../stylesheets/css/Opportunities.css";
import { getPosting, createApplication, getApplicationsByPosting } from "../helpers/services/user-service";

class ApplyOpportunity extends Component {
    constructor(props) {
        super(props);
        this.state = {
                uid: props.uid,
                pid: props.match.params.pid,
                title: "",
                type: "",
                description: "",
                email: "",
                applied: false
        }
    }

    componentDidMount() {
        const { pid, uid } = this.state;
        const int_uid = parseInt(uid);

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

        getApplicationsByPosting(pid).then(
            (r) => {
                var applied = false;
                for (var i = 0; i < r.data.length; i++) {
                    if (r.data[i].applicant.id === int_uid) {
                        applied = true;
                        break;
                    }
                }
                this.setState({ applied: applied });
            }
        );
    }

    handleChange(event) {
        this.setState({email: event.target.value});
    }

    handleApply() {
        const { uid, pid, email, applied } = this.state;
        if (!applied) {
            createApplication(uid, pid, email).then(
                (r) => {
                    this.props.history.push("/opportunity/myopportunities");
                }
            );
        }
    }

    render() { 
        var isValid = this.state.email;
        return (
            <div className="opportunity-page-container">
                <div className="shadow p-3 mb-5 bg-white rounded">
                    <h3 className="opportunity-page-header">Applying for Opportunity</h3>
                </div>

                <div className="opportunity-grid">
                    <div className="grid-pTitle">
                        <h5>Title</h5>
                        <p className="text-center pTitle-content">{this.state.title}</p>
                    </div>
                    <div className="grid-pType">
                        <h5>Type</h5>
                        <p className="text-center pType-content">{this.state.type}</p>
                    </div>
                    <div className="grid-pDescription">
                        <h5>Description</h5>
                        <p className="pDescription-content">{this.state.description}</p>
                    </div>
                </div>

                <div className="opportunity-formGroup">
                    <label htmlFor="email">Your application status will be notified through email. Please leave us an active email.</label>
                    <input type="email"
                           id="email"
                           className="form-control"
                           placeholder="email@address.com"
                           onChange={(event) => this.setState({email: event.target.value})} />
                </div>
                <button className="btn btn-primary opportunity-formButtons" 
                        disabled={!isValid || this.state.applied}
                        onClick={() => this.handleApply()}>
                    Apply
                </button>
                <button onClick={() => this.props.history.goBack()}
                    className="btn btn-secondary opportunity-formButtons">
                    Cancel
                </button>
                {this.state.applied &&
                <div className="badge badge-danger">you already applied for this opportunity!</div>}
            </div>
        );
    }
}
 
export default ApplyOpportunity;