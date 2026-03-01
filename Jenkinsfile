pipeline {
  agent any
  tools { maven 'Maven' } // ensure Jenkins global tools has a Maven named 'Maven'
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build') {
      steps {
        sh 'mvn -B clean package'
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

