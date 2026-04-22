/* Requires the Docker Pipeline plugin */
pipeline {
    agent any
    options {
        timeout(time: 10, unit: 'MINUTES')  // ✅ aborts automatically if exceeded
    }
    stages {
        stage('Test') {
            when {
                anyOf {
                    branch 'main'
                    branch 'development'
                }
                beforeAgent true  // ✅ skips the stage entirely without allocating a node
            }
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
            when {
                anyOf {
                    branch 'main'
                    branch 'development'
                }
                beforeAgent true  // ✅ skips the stage entirely without allocating a node
            }
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                withAWS([credentials: 'aws-eb-credentials', region: 'us-west-1']) {
                    withCredentials([
                            string(credentialsId: 'db-url', variable: 'DB_URL'),
                            string(credentialsId: 'db-username', variable: 'DB_USER'),
                            string(credentialsId: 'db-password', variable: 'DB_PASS')
                    ]) {
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
                                --version-label $VERSION_LABEL \
                                --option-settings \
                                Namespace=aws:elasticbeanstalk:application:environment,OptionName=SPRING_PROFILES_ACTIVE,Value=prod \
                                Namespace=aws:elasticbeanstalk:application:environment,OptionName=PORT,Value=8080 \
                                Namespace=aws:elasticbeanstalk:application:environment,OptionName=MYSQL_FULL_URL_PROD,Value=$DB_URL \
                                Namespace=aws:elasticbeanstalk:application:environment,OptionName=MYSQL_USER_PROD,Value=$DB_USER \
                                Namespace=aws:elasticbeanstalk:application:environment,OptionName=MYSQL_PASSWORD_PROD,Value=$DB_PASS

                            echo "Waiting for deployment to complete..."
                            for i in $(seq 1 24); do
                                STATUS=$(aws elasticbeanstalk describe-environments \
                                    --environment-names Orders-app-env \
                                    --query "Environments[0].Status" \
                                    --output text)
                                echo "Current status: $STATUS"
                                if [ "$STATUS" = "Ready" ]; then
                                    echo "Deployment complete"
                                    exit 0
                                fi
                                sleep 15
                            done
                            echo "Deployment timed out"
                            exit 1
                        '''
                    }
                }
            }
        }
    }
}
