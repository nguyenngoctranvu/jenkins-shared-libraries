def call(project, namespace, apiUrl, bucket, reportsvr) {
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
      credentialsId: "S3",
      usernameVariable: "S3_KEY",
      passwordVariable: "S3_SECRET"
    )
  ]) {
    sh """helm upgrade ${project} helm/reportingjob \
    -i -n ${namespace} --set image.tag=${currentBuild.displayName} \
    --set mongo.Url=${MONGO_URL} \
    --set oaApi.authToken=${API_AUTH_TOKEN} \
    --set oaApi.Url=${apiUrl} \
    --set bucket.Name=${bucket} \
    --set bucket.Key=${S3_KEY} \
    --set bucket.Secret=${S3_SECRET} \
    --set report.Server=${reportsvr} \
    """
  }
}