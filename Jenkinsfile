def gv
PUBLIC_IP = "18.206.209.221"
IMAGE_NAME = "vikas1412/node-website:2.0"
    
pipeline {
    agent any
    stages {
        stage("Initialize groovy") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("Testing") {
            steps {
                script {
                    gv.testApp()
                }
            } 
        }
        stage("Building Image for Maven website") {
            steps {
                script {
                    echo "Building app"
                    gv.buildImage()
                }
            }
        }
        stage("Deploying to ec2") {
            steps {
                script {
                    gv.deployApp()
                }
            } 
        }
    }
}