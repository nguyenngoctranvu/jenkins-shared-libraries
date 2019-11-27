def call(project, namespace, apiurl, bucket, reportsvr) {
  withCredentials([
    string(
      credentialsId: 'oaApi.authToken',
      variable: 'API_AUTH_TOKEN'
    ),
    string(
      credentialsId: 'mongo.Url',
      variable: 'MONGO_URL'
    ),
    string(
      credentialsId: 'S3.Key',
      variable: 'S3_KEY'
    ),
    string(
      credentialsId: 'S3.Secret',
      variable: 'S3_SECRET'
    )
  ]) {
    sh """helm upgrade ${project} helm/reportingjob \
    -i -n ${namespace} --set image.tag=${currentBuild.displayName} \
    --set mongo.Url=${MONGO_URL} \
    --set oaApi.authToken=${API_AUTH_TOKEN} \
    --set oaApi.Url=${apiurl} \
    --set bucket.Name=${bucket} \
    --set bucket.Key=${S3_KEY} \
    --set bucket.Secret=${S3_SECRET} \
    --set report.Server=${reportsvr} \
    """
  }
}