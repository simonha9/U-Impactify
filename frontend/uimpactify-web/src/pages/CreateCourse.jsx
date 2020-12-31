import React, { Component } from 'react';
import "../stylesheets/css/Courses.css";
import { createCourse } from "../helpers/services/course-service";

class CreateCourse extends Component {
    constructor(props) {
        super(props);
        this.state = {
            uid: props.uid,
            title: "",
            cost: 0,
            description: ""
        }
    }
    
    handleChangeName = (event) => {
        this.setState({ title: event.target.value });
    }

    handleChangeCost = (event) => {
        this.setState({ cost: event.target.value });
    }

    handleChangeDescription = (event) => {
        this.setState({ description: event.target.value });
    }

    handleSave = (event) => {
        event.preventDefault();
        const { uid, title, cost, description } = this.state;
        createCourse(title, cost, description, uid).then(
            (_) => {
                this.props.history.push("/courses/mycourses");
            }
        );
    }

    render() { 
        return (
            <div className="courses-page-container">
                <div className="shadow p-3 mb-5 bg-white rounded">
                    <h3 className="courses-page-header">New Course</h3>
                </div>

                <form className="course-formGroup">
                    <div className="form-group row">
                        <label htmlFor="title" className="col-sm-2 col-form-label">Course Name</label>
                        <div className="col-sm-10">
                            <input type="text" id="title" className="form-control" 
                                    onChange={(event) => this.handleChangeName(event)}/>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label htmlFor="cost" className="col-sm-2 col-form-label">Course Cost</label>
                        <div className="col-sm-2">
                            <input type="number" min="0" max="999" step="1" id="cost" className="form-control" 
                                    placeholder="$" onChange={(event) => this.handleChangeCost(event)}/>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label htmlFor="description" className="col-sm-2 col-form-label">Course Description</label>
                        <div className="col-sm-10">
                            <textarea type="text" id="description" className="form-control" rows="10" 
                                        onChange={(event) => this.handleChangeDescription(event)}/>
                        </div>
                    </div>

                    <button onClick={() => this.props.history.push("/courses/mycourses")}
                       className="btn btn-secondary course-formButton">
                        Cancel
                    </button>
                    <button type="submit" 
                            className="btn btn-primary course-formButton"
                            onClick={(event) => this.handleSave(event)}>
                        Create
                    </button>
                </form>
            </div>
        );
    }
}
 
export default CreateCourse;