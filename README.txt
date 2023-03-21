README PELICARE

How to set up your development environment from scratch

Step 1: Clone the repository from Github

Front End:
Step 1: Open Frontend folder with Visual Code
Step 2: Run 'npm install' in the terminal to update or download all node-modules.
Step 3: To build the front end use 'ng serve' in the terminal


Back End:
Step 1: Open Backend folder with IntelliJ
Step 2: Create new schema called 'pelicare' in MySQL Workbench. 
Step 3: Create a new user with full access and authorization to the ‘pelicare’ schema.
Step 4: In the map 'config' make a new file called 'application.properties'
	Set the databaseUsername and databasePassword as shown in
	application.properties.example with the userdata you created in step 3
Step 5: Set the secret key in application.properties with the key you received from your team

