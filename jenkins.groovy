pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "https://docker.io"
        IMAGE_NAME = "First-docker-image"
        IMAGE_TAG = "latest"
    }

    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image using the Dockerfile in the current directory
                    dockerImage = docker.build("${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Log in to the Docker registry
                    docker.withRegistry("${DOCKER_REGISTRY}", "docker-credentials-id") {
                        // Push the built Docker image to the registry
                        dockerImage.push("${IMAGE_TAG}")
                    }
                }
            }
        }

        stage('Run Docker Image') {
            steps {
                script {
                    // Run the Docker image
                    docker.run("--rm -p 8080:80 ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}")
                }
            }
        }
    }
}
