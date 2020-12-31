import React, { Fragment } from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Dashboard from "./pages/Dashboard.jsx";
import MyCourses from "./pages/MyCourses.jsx";
import CoursePage from "./pages/CoursePage.jsx";
import CreateCourse from "./pages/CreateCourse.jsx";
import ExploreCourse from "./pages/ExploreCourse.jsx";
import MyOpportunities from "./pages/MyOpportunities.jsx";
import ExploreOpportunities from "./pages/ExploreOpportunities.jsx";
import ManageOpportunity from "./pages/ManageOpportunity.jsx";
import CreateOpportunity from "./pages/CreateOpportunity.jsx";
import ApplyOpportunity from "./pages/ApplyOpportunity.jsx";

import DetailsOpportunity from "./pages/DetailsOpportunity.jsx";
import "bootstrap/dist/css/bootstrap.min.css";
import "shards-ui/dist/css/shards.min.css";
import bubbleBackground from "./img/double-bubble-outline.png";
import "./App.css";
import Sidebar from "./components/Sidebar";
import Landing from "./pages/Landing.jsx";
import SignUp from "./pages/SignUp.jsx";
import SignIn from "./pages/SignIn.jsx";
import Billing from "./pages/Billing.jsx";
import CalendarPlanner from "./pages/CalendarPlanner.jsx";
import { UserContextProvider } from "./components/UserContextProvider";
import PrivateRoute from "./components/PrivateRoute";

const styles = {
  main: {
    backgroundImage: `linear-gradient(to bottom, rgba(240, 240, 240, 0.4), rgba(240, 240, 240, 0.4)),url(${bubbleBackground})`,
    backgroundRepeat: "repeat",
    minHeight: "100vh",
  },
};

function App() {
  return (
    <div id="main-container" style={styles.main}>
      <UserContextProvider>
        <Sidebar />
        <BrowserRouter>
          <Switch>
            <Route path="/" exact component={Landing} />
            <Route path="/signup" exact component={SignUp} />
            <Route path="/signin" exact component={SignIn} />

            <Fragment>
              <PrivateRoute path="/dashboard" component={Dashboard} />
              <PrivateRoute path="/calendar" component={CalendarPlanner} />
              <PrivateRoute
                path="/courses/mycourses"
                exact
                component={MyCourses}
              />
              <PrivateRoute path="/courses/new" component={CreateCourse} />
              <PrivateRoute path="/courses/explore" component={ExploreCourse} />
              <PrivateRoute
                path="/courses/mycourses/:cid"
                component={CoursePage}
              />
              <PrivateRoute
                path="/opportunity/myopportunities"
                component={MyOpportunities}
              />
              <PrivateRoute
                path="/opportunity/explore"
                component={ExploreOpportunities}
              />
              <PrivateRoute
                path="/opportunity/apply/:pid"
                component={ApplyOpportunity}
              />
              <PrivateRoute
                path="/opportunity/manage/:pid"
                component={ManageOpportunity}
              />
              <PrivateRoute
                path="/opportunity/create/:type"
                component={CreateOpportunity}
              />
              <PrivateRoute
                path="/opportunity/details/:pid"
                component={DetailsOpportunity}
              />
              <PrivateRoute path="/billing" component={Billing} />
            </Fragment>
          </Switch>
        </BrowserRouter>
      </UserContextProvider>
    </div>
  );
}

export default App;
