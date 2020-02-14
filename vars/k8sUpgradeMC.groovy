def call() {
  withCredentials([
    usernamePassword(
      credentialsId: "LOG_ACCESS_KEY",
      usernameVariable: "LOG_ACCESS_KEY",
      passwordVariable: "LOG_SECRET_ACCESS_KEY"
    )
  ]) {
    sh """helm upgrade ${JOB_BASE_NAME}-${ENV} helm/${JOB_BASE_NAME} \
    -i -n mc-${ENV} \
    --set ingress.host=${MC_URL} \
    --set image.tag=${currentBuild.displayName} \
    --set log.group_name=${LOG_GROUP_NAME} \
    --set log.stream=${LOG_STREAM} \
    --set log.region=${LOG_REGION} \
    --set log.access_key=${LOG_ACCESS_KEY} \
    --set log.secret_access_key=${LOG_SECRET_ACCESS_KEY} \
    --set log.is_local=${LOG_IS_LOCAL} 
    """
  }
}