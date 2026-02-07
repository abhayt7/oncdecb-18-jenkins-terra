pipeline {
    agent {label 'slave'}
    stages {
        stage('Pull') {
            steps {
                git branch: 'main', url: 'https://github.com/abhayt7/47-cdec-app.git'
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
                sh ''' 
                   cd terraform/eks
                   terraform init
                   terraform apply -auto-approve
                   '''
           }
       }
     }
    }

stage('Destroy') {
            when {
                expression { params.ACTION == 'destroy' }
            }
            steps {
                input message: '⚠️ Are you sure you want to DESTROY all infra?', ok: 'Yes, Destroy'
                sh '''
                cd terraform/eks
                terraform destroy -auto-approve
                '''
            }
        }
    }
}
