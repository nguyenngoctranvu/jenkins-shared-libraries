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
    ),
    usernamePassword(
      credentialsId: "BANK_API",
      usernameVariable: "API_URL",
      passwordVariable: "AUTH_TOKEN"
    )
  ]) {
    sh """helm upgrade ${project} helm/mc-daily-job \
    -i -n ${namespace} --set image.tag=${currentBuild.displayName} \
    --set mongo.Url=$MONGO_URL,rabbit.Host=${rabbithost} \
    --set oaApi.authToken=$API_AUTH_TOKEN \
    --set oaApi.Url=${apiUrl} --set bankApi.Url=$API_URL --set bankApi.authToken=$AUTH_TOKEN
    """
  }
}