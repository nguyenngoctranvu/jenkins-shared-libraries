def call() {
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
      credentialsId: "mysql.oa",
      usernameVariable: "MYSQL_USER",
      passwordVariable: "MYSQL_PASS"
    ),
    usernamePassword(
      credentialsId: "S3",
      usernameVariable: "S3_KEY",
      passwordVariable: "S3_SECRET"
    ),
    usernamePassword(
      credentialsId: "BANK_API_AUTH_TOKEN",
      usernameVariable: "BLANK",
      passwordVariable: "BANK_API_AUTH_TOKEN"
    )
  ]) {
    sh """kubectl create secret generic ${JOB_BASE_NAME}-${ENV} \
    --from-literal=OA_API_AUTH_TOKEN=${OA_API_AUTH_TOKEN} \
    --from-literal=MONGO_URL=${MONGO_URL} \
    --from-literal=MYSQL_PASS=${MYSQL_PASS} \
    --from-literal=S3_SECRET=${S3_SECRET} \
    --from-literal=BANK_API_AUTH_TOKEN=${BANK_API_AUTH_TOKEN} \
    --dry-run -o yaml > helm/${JOB_BASE_NAME}/templates/${JOB_BASE_NAME}-${ENV}.yml
    """
    sh """helm upgrade ${JOB_BASE_NAME}-${ENV} helm/${JOB_BASE_NAME} \
    -i -n oa-${ENV} \
    --set image.tag=${currentBuild.displayName} \
    --set oaApi.Url=${OA_API_URL} \
    --set oaWeb.Url=${OA_WEB_URL} \
    --set bankApi.Url=${BANK_API_URL} \
    --set rabbit.Host=${RABBIT_HOST} \
    --set schedule.Hour=${SCHEDULE_HOUR} \
    --set schedule.Minute=${SCHEDULE_MINUTE} \
    --set interest.Schedule_Hour=${INTEREST_SCHEDULE_HOUR} \
    --set interest.Schedule_Minute=${INTEREST_SCHEDULE_MINUTE} \
    --set mysql.address=${MYSQL_ADDRESS} \
    --set mysql.user=${MYSQL_USER} \
    --set logger.Module=${JOB_BASE_NAME} \
    --set ingress.host.web=${OA_WEB_URL} \
    --set ingress.host.api=${OA_API_URL} \
    --set ingress.host.smstransfer=${SMS_TRANSFER_URL} \
    --set bucket.Name=${BUCKET_NAME} \
    --set bucket.Key=${S3_KEY} \
    --set report.Server=${REPORT_SERVER}
    """
  }
}
