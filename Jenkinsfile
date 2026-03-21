pipeline {
  agent any

  tools { maven 'Maven' } // set this name in Jenkins Global Tool Configuration

  environment {
    DOCKERHUB_CREDENTIALS = "${env.DOCKERHUB_CREDENTIALS}"   // Jenkins credentials ID (username/password)
    DOCKER_IMAGE = "${env.DOCKER_IMAGE}" // REPLACE
    IMAGE_TAG = "${env.BUILD_NUMBER}"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build') {
      steps {
        script {
          if (isUnix()) {
            sh '''
              if [ -f mvnw ]; then
                chmod +x mvnw || true
                ./mvnw -B clean package -DskipTests
              else
                mvn -B clean package -DskipTests
              fi
            '''
          } else {
            bat '''
              if exist mvnw.cmd (
                mvnw.cmd -B clean package -DskipTests
              ) else (
                mvn -B clean package -DskipTests
              )
            '''
          }
        }
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      }
    }

    stage('Docker Login') {
      steps {
        withCredentials([usernamePassword(credentialsId: env.DOCKERHUB_CREDENTIALS, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
        }
      }
    }

    stage('Docker Build') {
      steps {
        sh "docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} ."
        sh "docker tag ${DOCKER_IMAGE}:${IMAGE_TAG} ${DOCKER_IMAGE}:latest"
      }
    }

    stage('Docker Push') {
      steps {
        sh "docker push ${DOCKER_IMAGE}:${IMAGE_TAG}"
        sh "docker push ${DOCKER_IMAGE}:latest"
      }
    }

    stage('Info') {
      steps {
        echo "Built image: ${DOCKER_IMAGE}:${IMAGE_TAG}"
      }
    }
  }

  post {
    always {
      sh 'docker logout || true'
    }
    success { echo 'Pipeline succeeded' }
    failure { echo 'Pipeline failed' }
  }
}
