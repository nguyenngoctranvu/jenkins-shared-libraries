def call(item) {
    tag = "${currentBuild.displayName}"
    sh """echo item: ${item}"""
    if ( "${item}" == "null" ) {
        SERVICE = "${JOB_BASE_NAME}"
    } else {
        SERVICE = "${item}"
    }
    withCredentials([
    usernamePassword(
      credentialsId: "COGNITO_WEB_CLIENT_ID",
      usernameVariable: "BLANK",
      passwordVariable: "COGNITO_WEB_CLIENT_ID"
    ),
    usernamePassword(
      credentialsId: "LOG_ACCESS_KEY",
      usernameVariable: "LOG_ACCESS_KEY",
      passwordVariable: "LOG_SECRET_ACCESS_KEY"
    )
    ]) {
        sh """docker-compose build --parallel  \
        --build-arg SJMBT_GATEWAY_URL=${GW_URL} \
        --build-arg COGNITO_WEB_CLIENT_ID=${COGNITO_WEB_CLIENT_ID} \
        --build-arg COGNITO_USER_POOL_ID=${COGNITO_USER_POOL_ID} \
        --build-arg COGNITO_REGION=${COGNITO_REGION} \
        --build-arg API_DL_URL=${API_DL_URL} \
        --build-arg LOG_GROUP_NAME=${LOG_GROUP_NAME} \
        --build-arg LOG_STREAM=${LOG_STREAM} \
        --build-arg LOG_REGION=${LOG_REGION} \
        --build-arg LOG_ACCESS_KEY=${LOG_ACCESS_KEY} \
        --build-arg LOG_SECRET_ACCESS_KEY=${LOG_SECRET_ACCESS_KEY} \
        --build-arg ONBOARDING_URL=${ONBOARDING_URL} \
        --build-arg ENV=${ENV} \
        ${SERVICE}"""

        sh """sudo docker tag ${JOB_BASE_NAME}_${SERVICE} \
        hub.sjmex.io/${SERVICE}:${tag}"""
    }

    withCredentials([usernamePassword(
        credentialsId: "docker",
        usernameVariable: "USER",
        passwordVariable: "PASS"
    )]) {
        sh """sudo docker login \
            -u $USER -p $PASS https://hub.sjmex.io"""
    }
    sh """sudo docker image push \
        hub.sjmex.io/${SERVICE}:${tag}"""
}
