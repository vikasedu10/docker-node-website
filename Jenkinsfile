def gv

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
                    env.IMAGE_NAME = "vikas1412/node-website:1.3"
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