# Jumia Phone Book Task

The following repo contains a spring boot service that is used to load phone number from an Sqlite table and display metadata related to it.
The service is consumed by a React Web Application

## How to start

### Live Deployment

The app is deployed and live in the following link

https://jumia-task.vercel.app/

It is deployed using vercel with github integration

### Local Build

* Clone The directory in a folder
* After you are done cloning the project navigate to the main directory where the docker-compse.yml 
is located and 
run the command "docker-compose up" from that directory
* After a few minutes when the 
command is done and you can see from the logs in the terminal that the frontend project has been built and spring boot is running navigate to http://localhost:9090/ to the web app

* To run without docker run the backend application as a basic spring boot project and for the frontend run the commands "npm install" and "npm start", the project uses jdk 8

## Packages/modules
### services.customer
This is the spring boot backend project it conatins the following packages

* **metadata:** Contains classes related to fetching metadata related to phone number validations from a json file (and in the future from other sources if required) and returns maps mapping country names and codes and their corresponding validators.
* **matcher:**  Package responsible for consuming the metadata source and extracting the code from the phone number and matching it with required validators and returning the validation result
* **repository:**  Database access layer for the sqlite database
* **service:**  Business layer which acts as facade and returns the list of phone numbers
* **controllers:**  Basic spring controller layer for the rest api
* **logging:**  A custom logging utility package which is built upon Aspect Oriented Programming (Aspect4J) to provide an attribute @Loggable which logs entering exiting and exceptions in functions
* **exception:**  Package for custom defined exceptions
* **entities:**  Database class entities for hibernate/jpa
* **models:**  custom model classes
* **test/:**  Unit and integration tests for the layers
* **Dockerfile:**  docker file used by docker-compose

### jumia-phone-app
This is the Typescript based React application bundled using Create-React-App (CRA), it is build using Tailwind CSS and Chakra UI for the component system design. The access layer is implemented with React-Query which is capable of powerful data synchronization and caching mechanisms for ensuring the smoothest UX possible. The project is built upon my template react project: https://github.com/amrayman100/react-typescript-tailwind-template

* **components:** Contains all the shared UI components such as reusable dropdown inputs and a paginated table component
* **pages:**  Contains all the pages in the app which for now is only the base phone list page
* **service:** Contains the service that consumes the rest api
* **models:**  Contains the typescript models

### cypress-e2e
This package contains sample BDD tests using the testing library cypress https://www.cypress.io/
The tests are run against the deployed app https://jumia-task.vercel.app/ that can be changed in the file cypress.json and the backend layer is mocked using the mocking mechanism available in cypress
it can be configured to run locally by changing the base url in cypress.json

### Steps to run
* Navigate to the directory ./cypress-e2e
* run "npm install"
* run "npx cypress open"
* A user interface will open click on the file 
phone-book-view.spec.js for the tests to run
