def call() {
  withCredentials([
    usernamePassword(
      credentialsId: "COGNITO_WEB_CLIENT_ID",
      usernameVariable: "BLANK",
      passwordVariable: "COGNITO_WEB_CLIENT_ID"
    )
  ]) {
    sh """kubectl create secret generic ${JOB_BASE_NAME}-${ENV} \
    --from-literal=COGNITO_WEB_CLIENT_ID=${COGNITO_WEB_CLIENT_ID} \
    --dry-run -o yaml > helm/${JOB_BASE_NAME}/templates/${JOB_BASE_NAME}-${ENV}.yml
    """
    sh """helm upgrade ${JOB_BASE_NAME}-${ENV} helm/${JOB_BASE_NAME} \
    -i -n mc-${ENV} \
    --set ingress.host.gw=${GW_URL} \
    --set ingress.host.onboarding=${ONBOARDING_URL} \
    --set image.tag=${currentBuild.displayName} \
    --set cognito.region=${COGNITO_REGION} \
    --set cognito.user_pool_id=${COGNITO_USER_POOL_ID} \
    --set mission_control.url=${MISSION_CONTROL_URL} 
    """
  }
}