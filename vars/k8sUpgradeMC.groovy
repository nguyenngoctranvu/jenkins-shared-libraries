def call(project, namespace, feUrl) {
  withCredentials([
    usernamePassword(
      credentialsId: "SJMBT_GATEWAY_URL",
      usernameVariable: "SJMBT_GATEWAY_URL",
      passwordVariable: "BLANK"
    ),
    usernamePassword(
      credentialsId: "COGNITO_WEB_CLIENT_ID",
      usernameVariable: "BLANK",
      passwordVariable: "COGNITO_WEB_CLIENT_ID"
    ),
    usernamePassword(
      credentialsId: "COGNITO_USER_POOL_ID",
      usernameVariable: "COGNITO_USER_POOL_ID",
      passwordVariable: "BLANK"
    ),
    usernamePassword(
      credentialsId: "COGNITO_REGION",
      usernameVariable: "COGNITO_REGION",
      passwordVariable: "BLANK"
    )
  ]) {
        sh """helm upgrade ${project} helm/mc \
        -i -n ${namespace} \
        --set image.tag=${currentBuild.displayName} \
        --set ingress.host=${feUrl} \
        --set gateway.url=${SJMBT_GATEWAY_URL} \
        --set cognito.web_client_id=${COGNITO_WEB_CLIENT_ID} \
        --set cognito.user_pool_id=${COGNITO_USER_POOL_ID} \
        --set cognito.region=${COGNITO_REGION} \
        """
      }
}