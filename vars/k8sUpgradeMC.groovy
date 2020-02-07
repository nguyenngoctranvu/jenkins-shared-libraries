def call() {
    sh """helm upgrade ${JOB_BASE_NAME}-${ENV} helm/${JOB_BASE_NAME} \
    -i -n mc-${ENV} \
    --set image.tag=${currentBuild.displayName} \
    """
}