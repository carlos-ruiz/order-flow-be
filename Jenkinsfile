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
                    sh '''
                        VERSION_LABEL="${BUILD_NUMBER}-$(date +%Y%m%d-%H%M%S)"
                        JAR_FILE=$(ls target/*.jar | head -1)

                        # Upload JAR to S3
                        aws s3 cp $JAR_FILE s3://orders-app-xyz/orders-app/$VERSION_LABEL.jar

                        # Create new application version
                        aws elasticbeanstalk create-application-version \
                            --application-name orders-app \
                            --version-label $VERSION_LABEL \
                            --source-bundle S3Bucket=orders-app-xyz,S3Key=orders-app/$VERSION_LABEL.jar

                        # Deploy to environment
                        aws elasticbeanstalk update-environment \
                            --environment-name Orders-app-env \
                            --version-label $VERSION_LABEL
                            --option-settings Namespace=aws:elasticbeanstalk:application:environment,OptionName=SERVER_PORT,Value=8080
                    '''
                    awaitDeploymentCompletion('Orders-app-env')
                }
            }
        }
    }
}
