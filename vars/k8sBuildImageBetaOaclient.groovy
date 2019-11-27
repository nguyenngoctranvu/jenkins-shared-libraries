def call(image, dockerFile, apiUrl, sudo = true) {
    tag = "${currentBuild.displayName}"
    prefix = ""
    if (sudo) {
        prefix = "sudo "
    }
    sh """${prefix}docker image build \
        -t ${image}:${tag} -f ${dockerFile} --build-arg apiUrl=${apiUrl} ."""
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