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
    def shellCmds = "bash ./server-compose-cmds.sh ${IMAGE_NAME}"
    sshagent(['ec2-ssh-keypair']) {
        sh "scp -o StrictHostKeyChecking=no docker-compose.yml ${PUBLIC_IP_ENDPOINT}:/home/ec2-user"
        sh "scp server-compose-cmds.sh ${PUBLIC_IP_ENDPOINT}:/home/ec2-user"
        sh "scp docker-compose.yml ${PUBLIC_IP_ENDPOINT}:/home/ec2-user"
        sh "ssh ${PUBLIC_IP_ENDPOINT} ${shellCmds}"
    }
}

return this
