def call(project, namespace, rabbithost) {
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
    --set mongo.Url=${MONGO_URL},rabbit.Host=${rabbithost} \
    --set oaApi.authToken=${API_AUTH_TOKEN} \
    --set schedule.Hour=23,schedule.Minute=35 \
    --set oaApi.Url="https://oaapi-stag.sjmex.io""""
  }
}