def call(project, namespace, apiUrl, rabbithost) {
  withCredentials([
    usernamePassword(
      credentialsId: "API_AUTH_TOKEN",
      usernameVariable: "",
      passwordVariable: "API_AUTH_TOKEN"
    ),
    usernamePassword(
      credentialsId: "MONGO_URL",
      usernameVariable: "",
      passwordVariable: "MONGO_URL"
    )
  ]) {
    sh """helm upgrade ${project} helm/blotter-job \
    -i -n ${namespace} --set image.tag=${currentBuild.displayName} \
    --set mongo.Url=${MONGO_URL},rabbit.Host=${rabbithost} \
    --set oaApi.authToken=${API_AUTH_TOKEN} \
    --set oaApi.Url=${apiUrl} 
    """
  }
}