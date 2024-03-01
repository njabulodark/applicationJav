# Hollywood Bets Aviation Bot

This project consists of three main parts: a website utilizing React for the frontend, Node.js for the backend, and Selenium for automating interactions with the Hollywood Bets website.

## Project Overview

The Hollywood Bets Aviation Bot project aims to automate aviation betting on the Hollywood Bets website. The system is divided into the following components:

1. **Frontend Website (React):** 
   - The frontend provides a user-friendly interface for users to interact with the system. It includes features such as a login page and a form for entering Hollywood Bets account details and bet amounts.

2. **Backend Server (Node.js):** 
   - The backend server handles authentication, validation, and communication with the Selenium automation script. It ensures secure and efficient data exchange between the frontend and the automation component.

3. **Automation Script (Selenium):** 
   - The Selenium automation script is triggered by the backend server upon user request. It navigates to the Hollywood Bets website, logs in with the provided credentials, and executes the aviation betting process based on the user's input.

## How It Works

1. **User Interaction:**
   - Users access the website through their browsers.
   - They log in with their Hollywood Bets account credentials and specify the bet amount.

2. **Backend Processing:**
   - The backend server receives the user's input and validates the data.
   - Upon successful validation, it triggers the Selenium automation script.

3. **Automation Process:**
   - The Selenium script opens a browser window, navigates to the Hollywood Bets website, and logs in using the provided credentials.
   - It selects the aviation betting section and places the bet with the specified amount.
   - Once the betting process is complete, the script closes the browser window.

## Used Tools

- React.js
- Node.js
- MySQL
- Java
- HTML
- CSS
- JavaScript

## Preview

Here are some screenshots of the project's website:

### Login Page
![Login Page](https://github.com/njabulodark/applicationJav/assets/84667108/7185573d-43b6-4718-9dcc-dc9fff15862b)


### Betting Form
![image](https://github.com/njabulodark/applicationJav/assets/84667108/61dd9773-a0a8-441c-9a9e-1d49a745fd7e)


### Automation in Action
![Automation in Action](/path/to/automation_in_action_screenshot.png)

## Installation and Usage

1. Clone the repository.
2. Install dependencies for both the frontend and backend using npm.
3. Run the backend server using `npm start`.
4. Run the frontend website using `npm start`.
5. Ensure Selenium WebDriver is installed and configured properly.
6. Execute the automation script with appropriate parameters.

## Contributing

Contributions to this project are welcome. Feel free to open issues or pull requests to suggest improvements or report bugs.

## License

This project is licensed under the [MIT License](LICENSE).
