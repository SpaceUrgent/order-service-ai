pipeline {
    agent any

    environment {
        APP_NAME = "order-service-ai"
        IMAGE_NAME = "order-service-ai:latest"
    }

    stages {
        stage('Build Jar') {
            steps {
                withMaven(maven: 'maven-3') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {

            steps {
                sh 'echo $(whoami)'
                sh 'docker build -t ${IMAGE_NAME} .'
                sh 'echo ${IMAGE_NAME} built'
            }

        }

        stage('Deploy to k3s') {
            steps {
                sh """
                    export KUBECONFIG=${KCFG}
                    sed -i 's|image: .*|image: ${IMAGE_NAME}|g' k3s/deployment.yaml
                    kubectl apply -f k3s/deployment.yaml -n ${K8S_NAMESPACE}
                    kubectl rollout status deployment/${APP_NAME} -n ${K8S_NAMESPACE}
                """
            }
        }
    }
}
