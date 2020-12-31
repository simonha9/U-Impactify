import React, { Component } from 'react';
import "../../stylesheets/css/AssignmentsTab.css";
import DatePicker from 'react-datepicker';
import firebase from "../../firebase.js";

class AssignmentCreate extends Component {
    constructor(props) {
        super(props);
        this.state = {
            newAssignName: "",
            newAssignDue: null,
            newAssignFile: null,
            createFailed: false
        }
    }

    handleChangeName = (event) => {
        this.setState({ newAssignName: event.target.value });
    }

    handleChangeDue = (date) => {
        this.setState({ newAssignDue: date });
    }

    handleUploadHandout = (event) => {
        const fileName = event.target.value.split("\\").pop();
        document.getElementById("handout-upload-text").innerHTML = fileName;
        this.setState({ newAssignFile: event.target.files[0] });
    }

    handleClickCreate = () => {
        const firestoreDb = firebase.firestore();
        const storageRef = firebase.storage().ref();
        firestoreDb.collection('assignments').add({
            name: this.state.newAssignName,
            due: this.state.newAssignDue,
            courseId: this.props.cid
        }).then(
            (doc) => {
                const fileRef = storageRef.child(doc.id);
                fileRef.put(this.state.newAssignFile).then(
                    () => {
                        this.props.onBack();
                    },
                    (err) => {
                        this.setState({ createFailed: true })
                    }
                );
            }
        );
    }

    render() {
        const { newAssignName, newAssignDue, newAssignFile } = this.state;
        
        return(
            <div>
                <h3>New Assignment</h3>
                    <label htmlFor="new-assign-name">Name</label>
                    <input id="new-assign-name" 
                        className="form-control"
                        type="text"
                        value={newAssignName}
                        onChange={(e) => this.handleChangeName(e)}/>
                    <label htmlFor="new-assign-due">Due</label><br/>
                    <DatePicker id="new-assign-due" 
                                className="form-control"
                                selected={this.state.newAssignDue}
                                onChange={date => this.handleChangeDue(date)}
                                showTimeSelect
                                dateFormat="MMM dd, h:mm aa"/><br/>
                    <label htmlFor="new-assign-upload">Assignment Handout</label><br/>
                    <div className="custom-file" id="new-assign-upload-container">
                        <input type="file" 
                            className="custom-file-input"
                            id="new-assign-upload"
                            onChange={(e) => this.handleUploadHandout(e)} />
                        <label htmlFor="new-assign-upload" className="custom-file-label" id="handout-upload-text">
                            Upload handout here
                        </label>
                    </div>

                    <button className="btn btn-secondary float-right new-assign-button"
                            onClick={this.props.onBack}>
                        Back
                    </button>
                    <button className="btn btn-primary float-right new-assign-button"
                            disabled={!newAssignName || !newAssignDue || !newAssignFile}
                            onClick={this.handleClickCreate}>
                        Create
                    </button>
                    {this.state.createFailed &&
                    <div className="badge badge-danger">
                        Oops, something went wrong with uploading the assignment handout. Try again later...
                    </div>}
            </div>
        );
    }
}
 
export default AssignmentCreate;