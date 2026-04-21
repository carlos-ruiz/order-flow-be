/* Requires the Docker Pipeline plugin */
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                withAWS(credentials: 'aws-eb-credentials', region: 'us-west-1') {
                    awsEbApp(
                            applicationName: 'orders-app',
                            environmentName: 'Orders-app-env',
                            includes: 'target/*.jar',
                            versionLabel: "${env.BUILD_NUMBER}-${new Date().format('yyyyMMdd-HHmmss')}"
                    )
                }
            }
        }
    }
}
