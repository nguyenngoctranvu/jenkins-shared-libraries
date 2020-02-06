def call() {
  withCredentials([
    usernamePassword(
      credentialsId: "COGNITO_WEB_CLIENT_ID",
      usernameVariable: "BLANK",
      passwordVariable: "COGNITO_WEB_CLIENT_ID"
    )
  ]) {
    sh """helm upgrade ${JOB_BASE_NAME}-${ENV} helm/${JOB_BASE_NAME} \
    -i -n mc-${ENV} \
    --set ingress.host=${MC_URL} \
    --set env=${ENV} \
    --set api.dl.url=${DL_URL} \
    --set image.tag=${currentBuild.displayName} \
    --set gateway.url=${GW_URL} \
    --set cognito.region=${COGNITO_REGION} \
    --set cognito.user_pool_id=${COGNITO_USER_POOL_ID} \
    --set cognito.web_client_id=${COGNITO_WEB_CLIENT_ID}
    """
  }
}