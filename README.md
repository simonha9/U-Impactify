# U-Impactify

An online e-learning platform targeted at social work and marginalized groups for both students and teachers.  Teachers can teach courses in the way of: giving lectures (virtual + slides), creating assignments and tests.  Teachers can opt to be part of an organization, or work more as a freelancer.  Students can be from any background, part of an organization or by themselves and take said courses.

### Screenshots
![Landing Page](/frontend/uimpactify-web/public/landing_page.PNG)
*Landing Page*

![Signup Form](/frontend/uimpactify-web/public/sign_up_form.PNG)
*Sign Up Form*

![Dashboard](/frontend/uimpactify-web/public/dashboard.PNG)
*Dashboard*

![Course](/frontend/uimpactify-web/public/course.PNG)
*New Course*

![Course Dashboard](/frontend/uimpactify-web/public/course_dashboard.PNG)
*Lectures, Assignments, Quizzes*


## Building the project

### When building the project, create a new run configuration by:
1. Click dropdown arrow beside green play button
2. Select run configurations
3. In the left panel, scroll down to "Maven Build"
4. Right click and select "new configuration"
5. Name the configuration "clean install"
6. in Base directory field, click "Variables..." button and select "project_loc"
7. in Goals field, type "clean install"
8. In the checkboxes below, click "Skip Tests" to be true
9. Left click the project at the root level, then click your new run config to do a clean install
(10).  If running form the terminal, do "mvn clean install -DskipTests=true"

### Setting up the database
1. Go to https://www.postgresql.org/download/ and install the latest version for your OS
2. Make sure to download the pgAdmin 4 - console extension
3. Go to installation location/bin run pgAdmin 4 executable
4. Set master password to "pgAdmin"
5. Right click servers on the left side and create a new one
6. Name the server c01
7. Under the connection tab, specify the host as "localhost"
8. Under the same tab, put password as "postgres" and click save password
9. Click save and run the spring application

### Run the spring application
After building the project, do the following to run the spring application
1. Right click the root project, and select show in local terminal.  Alternatively, left click the root project and hit ctrl+alt+t
2. Type "mvn spring-boot:run"
3. Wait until you see the giant SPRING logo and it says that your server is up and running, hit ctrl+c to kill it.

### To see API documentation
1. Run the spring application
2. Go to http://localhost:8080/uimpactify/swagger-ui.html

### Running the front-end
Go to "project-coding-lads\frontend\uimpactify-web"
With node installed (https://nodejs.org/en/), run:
1. npm install
2. npm start  
The first command installs all dependencies.
The second command runs the react script after which, a new browser window should open the website on localhost.
