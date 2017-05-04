
pipeline{
node('master') {

 stage('Configure') {
                    env.PATH = "${tool 'mvn'}/bin:${env.PATH}"
                    }
 stage('Checkout') {
    git changelog: false, credentialsId: 'github', poll: false, url: 'https://github.com/rnsingh/RelNotes.git'
    timeout(time: 60, unit: 'SECONDS') 
    {
    // some block
    }
                    }
    stage('Build') {
    bat 'mvn -B -V -U -e clean package'
  }
 stage('Clean Workspace Post Build'){
  cleanWs()
   }
 }
}
