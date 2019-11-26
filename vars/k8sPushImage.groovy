 def call(image, sudo = true) {
    tag = "${currentBuild.displayName}"
    prefix = ""
    if (sudo) {
        prefix = "sudo "
    }
    sh """${prefix}docker pull \
        ${image}:${tag}"""
    sh """${prefix}docker image tag \
        ${image}:${tag} \
        ${image}:latest-stag"""
    withCredentials([usernamePassword(
        credentialsId: "docker",
        usernameVariable: "USER",
        passwordVariable: "PASS"
    )]) {
        sh """${prefix}docker login \
        -u $USER -p $PASS https://hub.sjmex.io"""
    }
    sh """${prefix}docker image push \
        ${image}:latest-stag"""
 }