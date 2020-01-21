def call(item) {
if ( "${item}" == "null" ) {
        SERVICE = "${JOB_BASE_NAME}"
} else {
        SERVICE = "${item}"
}
sh """kubectl -n mc-${ENV} \
rollout status deployment \
${SERVICE}"""
}
