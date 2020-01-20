def call(item) {
    tag = "${currentBuild.displayName}"
    if ( item != "" ) {
        SERVICE = item
    } else {
        SERVICE = ${JOB_BASE_NAME}
    }
    sh """docker-compose build \
        ${JOB_BASE_NAME}"""

    sh """sudo docker tag ${JOB_BASE_NAME}_${SERVICE} \
        hub.sjmex.io/${JOB_BASE_NAME}:${tag}"""

    withCredentials([usernamePassword(
        credentialsId: "docker",
        usernameVariable: "USER",
        passwordVariable: "PASS"
    )]) {
        sh """sudo docker login \
            -u $USER -p $PASS https://hub.sjmex.io"""
    }
    sh """sudo docker image push \
        hub.sjmex.io/${JOB_BASE_NAME}:${tag}"""
}
