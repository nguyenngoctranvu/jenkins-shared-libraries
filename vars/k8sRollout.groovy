def call(project) {
    sh """kubectl -n oa-stag \
        rollout status deployment \
        ${project}-stag"""
}