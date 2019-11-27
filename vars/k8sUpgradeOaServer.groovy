def call(project, namespace,apiUrl, dbUrl) {
  withCredentials([usernamePassword(
    credentialsId: "mysql.oa",
    usernameVariable: "USER",
    passwordVariable: "PASS"
  )]) {
        sh """helm upgrade ${project} helm/oa-server \
        -i -n ${namespace} \
        --set image.tag=${currentBuild.displayName} \
        --set ingress.host=${apiUrl} \
        --set db.address="${dbUrl}" \
        --set db.user=${USER} \
        --set db.password=${PASS}
        """
    }
}