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

        stage("Building Image for Maven website") {
            steps {
                script {
                    echo "Building app"
                    env.IMAGE_NAME = "vikas1412/node-website:1.1"
                    gv.buildImage()
                }
            }
        }

        stage("Deploying to ec2") {
            // when {
            //     expression {
            //         BRANCH_NAME = 'main'
            //     }
            // }
            steps {
                script {
                    gv.deployApp()
                }
            } 
        }
    }
}