pipeline {
    agent any 
    environment {
        registry = "kamalteja99/mypythonapp"
        //- update your credentials ID after creating credentials for connecting to Docker Hub
        registryCredential = "dd54db5b-46b5-485a-a2ed-66ca72e57689"
        dockerImage = ''
    }
    
    stages {
        stage('Cloning Git') {
            steps {
            checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/MedaKamalTeja/First-project.git']])            }
        }
    
    // Building Docker images
    stage('Building image') {
      steps{
        script {
          dockerImage = docker.build registry
        }
      }
    }
    
     // Uploading Docker images into Docker Hub
    stage('Upload Image') {
     steps{    
         script {
            docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
            }
        }
      }
    }
    
    
    // Running Docker container, make sure port 8096 is opened in 
    stage('Docker Run') {
     steps{
         script {
            bat "docker run %registry%"
         }
      }
    }
  }
}
