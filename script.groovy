def testApp() {
    echo "Testing node website"
}

def buildImage() {
    echo "Building Docker image for Node application"
    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh "docker build -t ${IMAGE_NAME} ."
        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
        sh "docker push ${IMAGE_NAME}"
    }
}

def deployApp() {
    echo "Deploying newly created Docker pushed image from Dockerhub to ec2 by `docker run`"
    def dockerRunImage = "docker run -p 3000:3000 -d ${IMAGE_NAME}"
    sshagent(['ec2-ssh-keypair']) {
        sh "ssh -o StrictHostKeyChecking=no ec2-user@13.235.78.154 ${dockerRunImage}"
    }
}

return this