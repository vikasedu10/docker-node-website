#!/usr/bin/env groovy

library identifier: 'jenkins-shared-library@master', retriever: modernSCM([
    $class: 'GitSCMSource',
    remote: 'https://gitlab.com/vikasedu10/jenkins-shared-library.git',
    credentialsId: 'gitlab-credentials'
])

def gv

pipeline {
    agent any
    environment {
        PUBLIC_IP_ENDPOINT = "ec2-user@18.206.209.221"
        IMAGE_NAME = "vikas1412/node-website:3.0"
    }
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
                    testApp()
                }
            } 
        }
        stage("Building Image for Maven website") {
            steps {
                script {
                    echo "Building app"
                    buildImage("${IMAGE_NAME}")
                    dockerLogin()
                    dockerPush("${IMAGE_NAME}")
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