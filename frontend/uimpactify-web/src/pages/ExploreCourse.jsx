import React, { Component } from 'react';
import "../stylesheets/css/Courses.css";
import { getAllCourses, enrollInCourse } from "../helpers/services/course-service";

class ExploreCourse extends Component {
    constructor(props) {
        super(props);
        this.state = {
            uid: props.uid,
            courseList: [],
            enrolled: props.location.state.enrolled
        }
        
    }

    componentDidMount() {
        const { enrolled } = this.state;
        getAllCourses().then(
            (r) => {
                var courseList = [];
                for (var i = 0; i < r.data.length; i++) {
                    const curr = r.data[i];
                    const { firstName, lastName } = curr.instructor.user;
                    if (!enrolled.includes(curr.id)) {
                        courseList.push({
                            cid: curr.id,
                            title: curr.courseName,
                            instructor: firstName + " " + lastName,
                            cost: curr.cost,
                            description: curr.courseDesc
                        });    
                    }
                }
                this.setState({ courseList: courseList })
            }
        );
    }

    handleClickCourse(id) {
        var courseButton = document.getElementById(id + "_button");
        var courseDetail = document.getElementById(id + "_details");
        if (courseDetail.style.display === "none") {
            courseDetail.style.display = "";
            courseButton.className += " c-expanded";    
        } else {
            courseDetail.style.display = "none";
            courseButton.className = courseButton.className.replace(" c-expanded", "");
        }
    }

    handleEnroll(cid) {
        enrollInCourse(cid, this.state.uid).then(
            (_) => {
                this.props.history.push("/courses/mycourses");
            }
        );
    }

    render() { 
        const { courseList } = this.state;

        return (
            <div className="courses-page-container">
                <div className="shadow p-3 mb-5 bg-white rounded">
                    <h3 className="courses-page-header">Explore Courses</h3>
                </div>

                <div className="courses-listing-available justify-content-center align-items-center">
                    <h3>Available Courses</h3>
                    {courseList.map((course) => (
                        <div key={course.cid}>
                            <button onClick={() => this.handleClickCourse(course.cid)}
                                    className="courses-listing-button"
                                    id={course.cid + "_button"}>
                                {course.title}
                            </button>
                            <div className="courses-listing-detail"
                                    id={course.cid + "_details"}
                                    style={{ display: "none" }}>
                                Name:
                                <h6>{course.title}</h6>                                
                                Instructor:
                                <h6>{course.instructor}</h6>
                                Cost:
                                <h6>${course.cost}</h6>
                                Description:
                                <h6>{course.description}</h6>
                                <button className="btn btn-danger btn-sm float-right"
                                        onClick={() => this.handleEnroll(course.cid)}>
                                    Enroll
                                </button>
                            </div>
                        </div>
                    ))}
                </div>

            </div>
        );
    }
}
 
export default ExploreCourse;