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
		  sh 'docker build -t weather-service .'
		  sh 'docker save -o /home/ubuntu/weather-service.tar weather-servcie'  
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
