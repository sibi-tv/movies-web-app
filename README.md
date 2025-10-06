## Project Overview

This project can be used to fetch a list of trending movies based on the TMDB API.
If you would like, you can also compile a list of your favorite movies!
Check out the repo here: https://github.com/sibi-tv/movies-web-app
Check out the repo here: https://github.com/sibi-tv/movies-web-app

### Prerequisite Details
Java version installed must be version 17.0.16 or higher
Node version installed must be version 20.19.5 or higher
Create an API Key at https://developer.themoviedb.org/

### How to Run

#### Step 1: open up 2 terminals into the project folder
Windows users can open up 2 powershells instead
On one terminal: cd backend
On the other terminal cd frontend

### Step 2: set up the API Key
Once you have the "API Read Access Token" from the TMDB API, go into your backend terminal and set it as an environment variable

#### Windows Users:
$env:TMDB_API_KEY="your access token"

#### Mac Users:
export TMDB_API_KEY="your access token"

#### Once you have added your access token, verify it has been set as an environment variable:
echo $TMDB_API_KEY (mac/linux)
echo $env:TMDB_API_KEY

### Step 3: Run the spring boot application (the backend):
Once you have set up your access token in your environment variables, run the Spring Boot Project

java -jar target/backend-0.0.1-SNAPSHOT.jar

If all works correctly you should see "Tomcat started on port 8080" in the logs, along with "Started BackendApplication"

#### (Mac/Linux users only) Step 2 File permission troubleshooting
Mac and Linux users may experience some trouble with file permissions when trying to run the backend.
If this happens, please try either of the following:

In your backend terminal, run:

chmod +x mvnw

If that doesn't work, from your root directory, run:

find . -maxdepth 1 -type d -exec chmod +xrw {} \

#### If you experience any other issues, double check that your Java Version is 17.0.16 or higher
java -version

### Step 4: Run the react vite application (the frontend):
In the frontend terminal, run the following commands:

npm ci
npm run dev

You should see that the application is available on "http://localhost:3000" if Port 3000 is not in use, or a different port if it is in use

ctrl + left-mouse-click the link to open the application and feel free to explore!


