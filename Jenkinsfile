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
            if (fileExists('mvnw')) {
              sh './mvnw -B clean package'
            } else {
              sh 'mvn -B clean package'
            }
          } else {
            if (fileExists('mvnw.cmd')) {
              bat 'mvnw -B clean package'
            } else {
              bat 'mvn -B clean package'
            }
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
