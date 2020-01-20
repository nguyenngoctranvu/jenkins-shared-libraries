def call() {
    sh """kubectl -n oa-${ENV} \
        rollout status deployment \
        ${JOB_BASE_NAME}-${ENV}"""
}
