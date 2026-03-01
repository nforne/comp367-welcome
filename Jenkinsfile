pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build') {
      steps {
        script {
          if (isUnix()) {
            sh '''
              if [ -f mvnw ]; then
                chmod +x mvnw || true
                ./mvnw -B clean package
              else
                mvn -B clean package
              fi
            '''
          } else {
            bat '''
              if exist mvnw.cmd (
                mvnw.cmd -B clean package
              ) else (
                mvn -B clean package
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
    stage('Info') {
      steps {
        echo "JENKINS_URL = ${env.JENKINS_URL}"
        echo "BUILD_ID = ${env.BUILD_ID}"
      }
    }
  }
  post {
    success { echo 'Build succeeded' }
    failure { echo 'Build failed' }
  }
}
