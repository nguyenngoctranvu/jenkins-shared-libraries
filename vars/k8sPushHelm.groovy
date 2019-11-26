def call(project, chartVersion, museumAddr) {
    withCredentials([usernamePassword(
        credentialsId: "chartmuseum", 
        usernameVariable: "USER", 
        passwordVariable: "PASS")]) {
        sh "helm package helm/${project}"
        packageName = "${project}-${chartVersion}.tgz"
        if (chartVersion == "") {
            packageName = sh(returnStdout: true, script: "ls ${project}*").trim()
        }
        sh """curl -u $USER:$PASS --data-binary "@${packageName}" https://${museumAddr}/api/charts"""
    }
}
