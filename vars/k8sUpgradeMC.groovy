def call() {
  sh """helm upgrade ${JOB_BASE_NAME}-${ENV} helm/${JOB_BASE_NAME} \
  -i -n mc-${ENV} \
  --set ingress.host=${MC_URL} \
  --set image.tag=${currentBuild.displayName} \
  """
}