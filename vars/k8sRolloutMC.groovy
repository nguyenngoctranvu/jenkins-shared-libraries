def call() {
    sh """kubectl -n mc-${ENV} \
        rollout status deployment \
        ${JOB_BASE_NAME}-${ENV}"""
}
