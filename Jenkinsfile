pipeline {
    agent any
    
    stages {
        stage('Test') {
            steps {
                echo '✅ Jenkins pipeline is working!'
                echo "Branch: ${env.GIT_BRANCH}"
            }
        }
    }
}