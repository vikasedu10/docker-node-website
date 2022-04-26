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
    echo "Deploying newly created Docker pushed image from Dockerhub to ec2 using `docker compose`"
    def dockerComposeRunImage = "docker-compose -f docker-compose.yml up --detach"
    sshagent(['ec2-ssh-keypair']) {
        sh "scp -o StrictHostKeyChecking=no docker-compose.yml ec2-user@18.206.209.221:/home/ec2-user"
        sh "ssh -o StrictHostKeyChecking=no ec2-user@18.206.209.221 ${dockerComposeRunImage}"
    }
}

return this
