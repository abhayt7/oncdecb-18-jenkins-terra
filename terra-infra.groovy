pipeline {
    agent {label 'slave'}
    stages {
        stage('Pull') {
            steps {
                git branch: 'main', url: 'https://github.com/Anilbamnote/47-cdec.git'
            }
        }
        stage('Test') {
            steps {
               sh '''
                cd terraform/eks
                terraform init
                terraform plan
                '''
            }
        }
        stage('Deploy') {
           steps {
               sh ''' cd /terraform/eks
                   terraform init
                   terraform apply '''
           }
       }
     }
    }
