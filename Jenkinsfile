pipeline {
    agent {
    label 'master'
    }
stages {
    stage ('Checkout') {
      steps {
	    node ('master') {
		   checkout scm
		   }
		}
    }
    stage ('Build') {
	  steps {
	    node ('master') {
		   sh 'cd $WORKSPACE; mvn clean install'
		   }
		}
    }
	stage ('Docker_Image') {
	  steps {
	    node ('master') {
		  sh 'docker build -t java_service .'
		  }
		}
    }		
    stage ('Deploy') {
      steps {
	    node ('master') {
		  sh 'cd /opt; ansible-playbook deploy.yaml'
		  }
		}
    }
 }
}
