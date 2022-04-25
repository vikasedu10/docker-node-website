def testApp() {
    echo "Testing node website"
}

def buildApp() {
    echo "Building Docker image for Node application"
    def dockerCreateImage = "docker build -t ${IMAGE_NAME} ."
    sshagent(['ec2-ssh-keypair']) {
        sh "ssh -o StrictHostKeyChecking=no ec2-user@13.235.78.154 ${dockerCreateImage}"
    }
}

def pushImage() {
    echo "Pushing newly created Docker image to Dockerhub for node application to ec2"
    def dockerPushImage = "docker push ${IMAGE_NAME}"
    sshagent(['ec2-ssh-keypair']) {
        sh "ssh -o StrictHostKeyChecking=no ec2-user@13.235.78.154 ${dockerPushImage}"
    }
}

def deployApp() {
    echo "Deploying newly created Docker pushed image from Dockerhub to ec2 by `docker run`"
    def dockerRunImage = "docker run ${IMAGE_NAME}"
    sshagent(['ec2-ssh-keypair']) {
        sh "ssh -o StrictHostKeyChecking=no ec2-user@13.235.78.154 ${dockerRunImage}"
}

return this