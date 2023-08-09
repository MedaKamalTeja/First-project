# Use a suitable base image
FROM node:alpine

# Set the working directory
WORKDIR /app

# Install git
RUN apk update && apk add git

# Clone the repository
RUN git clone https://github.com/MedaKamalTeja/First-project.git

# Set the working directory within the cloned repository
WORKDIR /app/FirstProject

# Run the app.js file
CMD node app.js

