def call(image, dockerFile, sudo = true) {
    tag = "${currentBuild.displayName}"
    prefix = ""
    if (sudo) {
        prefix = "sudo "
    }
    sh """${prefix}docker image build \
        -t ${image}:${tag} -f ${dockerFile}"""
    withCredentials([usernamePassword(
        credentialsId: "docker",
        usernameVariable: "USER",
        passwordVariable: "PASS"
    )]) {
        sh """${prefix}docker login \
            -u $USER -p $PASS https://hub.sjmex.io"""
    }
    sh """${prefix}docker image push \
        ${image}:${tag}"""
}