def call(project, namespace, clientUrl) {
  sh """helm upgrade ${project} helm/oa-client \
  -i -n ${namespace} --set image.tag=${currentBuild.displayName} \
  --set ingress.host=${clientUrl} --dry-run --debug
  """
}