pipeline {
  agent any // run on any available agent

  stages {
    stage('Checkout') {
      steps {
        // 'checkout scm' requires the job to be a Multibranch Pipeline or Pipeline from SCM.
        // If this Jenkinsfile is pasted into a single-branch Pipeline job, replace this with an explicit git step.
        checkout scm
      }
    }

    stage('Build') {
      steps {
        script {
          // Detect platform and run the appropriate commands.
          // On Unix agents use sh; on Windows agents use bat.
          if (isUnix()) {
            sh '''
              # If the Maven wrapper exists, ensure it's executable and use it for a reproducible build.
              # The wrapper downloads the correct Maven version for the project.
              if [ -f mvnw ]; then
                chmod +x mvnw || true
                ./mvnw -B clean package
              else
                # Fall back to system-installed mvn if wrapper is not present.
                mvn -B clean package
              fi
            '''
          } else {
            bat '''
              REM On Windows, prefer mvnw.cmd if present; otherwise use system mvn.
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
        // Save build artifacts so they are available from the Jenkins build page.
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
      }
    }

    stage('Info') {
      steps {
        // Print useful environment variables for debugging and traceability.
        echo "JENKINS_URL = ${env.JENKINS_URL}"
        echo "BUILD_ID = ${env.BUILD_ID}"
      }
    }
  }

  post {
    // Post actions run after the pipeline completes; useful for notifications or cleanup.
    success { echo 'Build succeeded' }
    failure { echo 'Build failed' }
  }
}
