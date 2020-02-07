def call(item) {
  if ( "${item}" == "null" ) {
    SERVICE = "${JOB_BASE_NAME}"
  } else {
    SERVICE = "${item}"
  }
  withCredentials([
    usernamePassword(
      credentialsId: "OA_API_AUTH_TOKEN",
      usernameVariable: "BLANK",
      passwordVariable: "OA_API_AUTH_TOKEN"
    ),
    usernamePassword(
      credentialsId: "MONGO_URL",
      usernameVariable: "BLANK",
      passwordVariable: "MONGO_URL"
    ),
    usernamePassword(
      credentialsId: "BANK_API_AUTH_TOKEN",
      usernameVariable: "BLANK",
      passwordVariable: "BANK_API_AUTH_TOKEN"
    ),
    usernamePassword(
      credentialsId: "AWS_ACCESS_KEY_ID",
      usernameVariable: "BLANK",
      passwordVariable: "AWS_ACCESS_KEY_ID"
    ),
    usernamePassword(
      credentialsId: "AWS_SECRET_KEY",
      usernameVariable: "BLANK",
      passwordVariable: "AWS_SECRET_KEY"
    ),
    usernamePassword(
      credentialsId: "COGNITO_WEB_CLIENT_ID",
      usernameVariable: "BLANK",
      passwordVariable: "COGNITO_WEB_CLIENT_ID"
    ),
    usernamePassword(
      credentialsId: "SJMBT_EMAIL_AUTH_PASS",
      usernameVariable: "BLANK",
      passwordVariable: "SJMBT_EMAIL_AUTH_PASS"
    )
  ]) {
    sh """helm upgrade ${SERVICE} helm/${SERVICE} \
    -i -n mc-${ENV} \
    --set ingress.host.gw=${GW_URL} \
    --set ingress.host.dl=${DL_URL} \
    --set mongo.Url=${MONGO_URL} \
    --set bankApi.Url=${BANK_API_URL} \
    --set bankApi.authToken=${BANK_API_AUTH_TOKEN} \
    --set oaApi.Url=${OA_API_URL} \
    --set oaApi.authToken=${OA_API_AUTH_TOKEN} \
    --set aws.secret_access_key=${AWS_SECRET_KEY} \
    --set aws.access_key_id=${AWS_ACCESS_KEY_ID} \
    --set aws.region=${AWS_REGION} \
    --set image.tag=${currentBuild.displayName} \
    --set cognito.region=${COGNITO_REGION} \
    --set cognito.user_pool_id=${COGNITO_USER_POOL_ID} \
    --set cognito.web_client_id=${COGNITO_WEB_CLIENT_ID} \
    --set sjmbt.admin_email_addr=${SJMBT_ADMIN_EMAIL_ADDR} \
    --set sjmbt.email_auth_user=${SJMBT_EMAIL_AUTH_USER} \
    --set sjmbt.email_auth_pass=${SJMBT_EMAIL_AUTH_PASS} 
    """
  }
}