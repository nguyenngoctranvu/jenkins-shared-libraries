def call(project, namespace, apiurl, bucket) {
  withCredentials([
    string(
      credentialsId: 'oaApi.authToken',
      variable: 'API_AUTH_TOKEN'
    ),
    string(
      credentialsId: 'mongo.Url',
      variable: 'MONGO_URL'
    )
  ]) {
    sh """helm upgrade ${project} helm/mc-daily-job \
    -i -n ${namespace} --set image.tag=${currentBuild.displayName} \
    --set mongo.Url=${MONGO_URL} \
    --set oaApi.authToken=${API_AUTH_TOKEN} \
    --set schedule.Hour=23,schedule.Minute=35 \
    --set oaApi.Url=${apiurl} \
    --set bucket.Name=${bucket} --dry-run --debug
    """
  }
}