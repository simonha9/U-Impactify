import React from "react";
import "../stylesheets/css/Landing.css";
import landingImage from "../img/landing-img.jpg";

class Landing extends React.Component {
  render() {
    return (
      <div className="about-section">
        <div className="about-text">
          <h5>What is Uimpactify?</h5>
          <h2>
            An online learning platform for social entrepreneurs and
            intrapreneurs
          </h2>
          <div
            className="get-started"
            onClick={() => {
              window.location.href = "/signup";
            }}
          >
            GET STARTED
          </div>
        </div>
        <img className="about-image" src={landingImage} alt="land page pic" />
      </div>
    );
  }
}
export default Landing;
