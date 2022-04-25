def gv

pipeline {
    agent any

    // environment {
    // }

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

        stage("Building & Pushing Image") {
            steps {
                script {
                    env.IMAGE_NAME = "vikas1412/node-website:1.1"
                    gv.buildApp()
                    gv.pushImage()
                }
            }
        }

        stage("Deploying to ec2") {
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    gv.deployApp()
                }
            } 
        }
    }
}