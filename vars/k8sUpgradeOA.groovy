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
    sh """helm upgrade ${JOB_BASE_NAME}-${ENV} helm/${JOB_BASE_NAME} \
    -i -n oa-${ENV} \
    --set image.tag=${currentBuild.displayName} \
    --set mongo.Url=${MONGO_URL} \
    --set oaApi.authToken=${OA_API_AUTH_TOKEN} \
    --set oaApi.Url=${OA_API_URL} \
    --set oaWeb.Url=${OA_WEB_URL} \
    --set bankApi.Url=${BANK_API_URL} \
    --set bankApi.authToken=${BANK_API_AUTH_TOKEN} \
    --set rabbit.Host=${RABBIT_HOST} \
    --set schedule.Hour=${SCHEDULE_HOUR} \
    --set schedule.Minute=${SCHEDULE_MINUTE} \
    --set interest.Schedule_Hour=${INTEREST_SCHEDULE_HOUR} \
    --set interest.Schedule_Minute=${INTEREST_SCHEDULE_MINUTE} \
    --set mysql.address=${MYSQL_ADDRESS} \
    --set mysql.user=${MYSQL_USER} \
    --set mysql.password=${MYSQL_PASS} \
    --set logger.Module=${JOB_BASE_NAME} \
    --set ingress.host.web=${OA_WEB_URL} \
    --set ingress.host.api=${OA_API_URL} \
    --set ingress.host.sms-transfer=${SMS_TRANSFER_URL} \
    --set bucket.Name=${BUCKET_NAME} \
    --set bucket.Key=${S3_KEY} \
    --set bucket.Secret=${S3_SECRET} \
    --set report.Server=${REPORT_SERVER}
    """
  }
}